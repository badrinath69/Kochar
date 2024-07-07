import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.pivotalsoft.hungerz.Apis.RetrofitClient.IMAGE_PRODUCT_URL
import com.pivotalsoft.hungerz.Interface.CartProductClickListener
import com.pivotalsoft.hungerz.R
import com.pivotalsoft.hungerz.model.Product
import com.pivotalsoft.kochar.api.RetrofitClient
import com.pivotalsoft.kochar.modal.CartDataModal
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.String
import java.util.*

fun onMinusClick(cartItemsBean: CartDataModal) {
    val productArrayList: ArrayList<CartDataModal> = arrayListOf()
    val i: Int = productArrayList.indexOf(cartItemsBean)
    if (cartItemsBean.no_units > 1.toString()) {
        val quantity: Int = cartItemsBean.no_units - 1
        val itemTotal: Int = ((cartItemsBean.productPrice).toInt() * quantity)
        Log.d("TAG", "onMinusClick: $quantity")
        val updatedProduct = CartDataModal(cartItemsBean.photo,
            cartItemsBean.productdes,
            cartItemsBean.productPrice,
            itemTotal.toString(),
            cartItemsBean.userid,
            quantity.toString(),
            cartItemsBean.product_id,
            cartItemsBean.cartitemid,
            cartItemsBean.added_on
        )
        productArrayList.remove(cartItemsBean)
        productArrayList.add(i, updatedProduct)
        updateQuantity(updatedProduct)
        cartAdapter.notifyDataSetChanged()
        calculateCartTotal()
    }

}


fun onPlusClick(cartItemsBean: CartDataModal) {
    val productArrayList: ArrayList<CartDataModal> = arrayListOf()
    val i: Int = productArrayList.indexOf(cartItemsBean)
    val quantity: Int = cartItemsBean.no_units.toInt() + 1
    val itemTotal: Int = (cartItemsBean.producttotalprice).toInt() * quantity
    Log.d("TAG", "onPlusClick: $quantity")
    val updatedProduct = CartDataModal(cartItemsBean.photo,
        cartItemsBean.productdes,
        cartItemsBean.productPrice,
        itemTotal.toString(),
        cartItemsBean.userid,
        quantity.toString(),
        cartItemsBean.product_id,
        cartItemsBean.cartitemid,
        cartItemsBean.added_on
    )
    productArrayList.remove(cartItemsBean)
    productArrayList.add(i, updatedProduct)
    Log.e("QUNATITY", "" + updatedProduct.no_units)
    updateQuantity(updatedProduct)
    cartAdapter.notifyDataSetChanged()
    calculateCartTotal()
}



















fun updateQuantity(product: CartDataModal) {
    val call: Call<ResponseBody> = RetrofitClient.getInstance().getApi().updateCart(
        tokenValue,
        product.getCartid(),
        String.valueOf(product.getFinalprice()),
        String.valueOf(product.getQuantity()),
        String.valueOf(product.getItemtotal()),
        product.getAddedat()
    )
    call.enqueue(object : Callback<ResponseBody?> {
        override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
            if (response.isSuccessful) {
                val baseResponse = response.body()
            }
        }

        override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {}
    })

}


fun calculateCartTotal() {
    val productArrayList: ArrayList<CartDataModal> = arrayListOf()
    var grandTotal = 0
    for (order in productArrayList) {
        grandTotal += (order.producttotalprice).toInt() //* order.no_units
        val userid = order.userid
        Log.d("TAG", "calculateCartTotal: $userid")
    }
    Log.e("TOTAL", "" + productArrayList.size + "Items | " + "  DISCOUNT : " + grandTotal)
    txtSubTotal.setText(productArrayList.size.toString() + " Items | Rs " + grandTotal)
    if (grandTotal >= minimumOrder) {
        btnCheckOut.setVisibility(View.VISIBLE)
        txtAlert.setVisibility(View.GONE)
    } else {
        btnCheckOut.setVisibility(View.INVISIBLE)
        txtAlert.setVisibility(View.VISIBLE)
        txtAlert.setText("Minimum Order Must Be Greater Than Rs.$minimumOrder*")
    }
    val finalGrandTotal = grandTotal
    Log.d("TAG", "calculateCartTotal2: $storeId")
    btnCheckOut.setOnClickListener(View.OnClickListener { //
        val intent = Intent(this@CartActivity, AddressListActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra("Checkout", true)
        intent.putExtra("grandTotal", finalGrandTotal)
        intent.putExtra("storeId", storeId)
        intent.putExtra("storeName", storeName)
        intent.putExtra("storeFcmKey", storeFcmKey)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    })
    if (productArrayList.size == 0) {
        setContentView(R.layout.emptycart)
        val btnGotohome = findViewById<View>(R.id.btnGotohome) as Button
        btnGotohome.setOnClickListener {
            val intent = Intent(this@CartActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("storeId", storeId)
            intent.putExtra("storeName", storeName)
            intent.putExtra("storeFcmKey", storeFcmKey)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
    }
}














































package com.pivotalsoft.hungerz.adapters
import com.pivotalsoft.hungerz.Interface.CartProductClickListener
import com.pivotalsoft.hungerz.R
import com.pivotalsoft.hungerz.model.Product
import butterknife.BindView
import butterknife.ButterKnife
import com.pivotalsoft.hungerz.Apis.RetrofitClient.IMAGE_PRODUCT_URL

class CartAdapter(
    private val mContext: Context,
    productsModelList: List<Product>,
    productClickListener: CartProductClickListener
) :
    RecyclerView.Adapter<com.pivotalsoft.hungerz.adapters.CartAdapter.MyViewHolder>() {
    @BindView(R.id.thumbnail1)
    var thumbnail1: ImageView? = null

    @BindView(R.id.product_name)
    var productName: TextView? = null

    @BindView(R.id.product_Price)
    var productPrice: TextView? = null

    @BindView(R.id.final_product_Price)
    var finalProductPrice: TextView? = null

    @BindView(R.id.product_minus)
    var productMinus: TextView? = null

    @BindView(R.id.product_quantity)
    var productQuantity: TextView? = null

    @BindView(R.id.product_plus)
    var productPlus: TextView? = null

    @BindView(R.id.btnFavourite)
    var btnFavourite: TextView? = null

    @BindView(R.id.linearSaveItem)
    var linearSaveItem: LinearLayout? = null

    @BindView(R.id.btnRemove)
    var btnRemove: TextView? = null

    @BindView(R.id.linearremove)
    var linearremove: LinearLayout? = null
    private val productsModelList: List<Product>
    private val productClickListener: CartProductClickListener

    inner class MyViewHolder(view: View?) : RecyclerView.ViewHolder(
        view!!
    ) {
        @BindView(R.id.thumbnail1)
        var thumbnail1: ImageView? = null

        @BindView(R.id.product_name)
        var productName: TextView? = null

        @BindView(R.id.product_Price)
        var productPrice: TextView? = null

        @BindView(R.id.final_product_Price)
        var final_product_Price: TextView? = null

        @BindView(R.id.product_minus)
        var productMinus: TextView? = null

        @BindView(R.id.product_quantity)
        var productQuantity: TextView? = null

        @BindView(R.id.product_plus)
        var productPlus: TextView? = null

        @BindView(R.id.btnFavourite)
        var btnFavourite: TextView? = null

        @BindView(R.id.linearSaveItem)
        var linearSaveItem: LinearLayout? = null

        @BindView(R.id.btnRemove)
        var btnRemove: TextView? = null

        @BindView(R.id.linearremove)
        var linearremove: LinearLayout? = null

        @BindView(R.id.img_remove)
        var imgRemove: ImageView? = null

        @BindView(R.id.product_unit)
        var product_unit: TextView? = null

        init {
            ButterKnife.bind(this, view)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): com.pivotalsoft.hungerz.adapters.CartAdapter.MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.cart_product, parent, false)
        return com.pivotalsoft.hungerz.adapters.CartAdapter.MyViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: com.pivotalsoft.hungerz.adapters.CartAdapter.MyViewHolder,
        position: Int
    ) {
        val productsModel: Product = productsModelList[position]

        // loading album cover using Glide library
        Glide.with(mContext).load(IMAGE_PRODUCT_URL + productsModel.getProductpic())
            .into(holder.thumbnail1)
        Log.d("cart", "onBindViewHolder: " + productsModel.getTitle())
        holder.productName.setText(productsModel.getTitle())
        holder.productQuantity.setText("" + productsModel.getQuantity())
        holder.product_unit.setText("" + productsModel.getItemtotal())
        holder.productPrice.setText(mContext.resources.getString(R.string.Rs) + productsModel.getFinalprice())
        /*holder.productPrice.setPaintFlags(holder.productPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.productPrice.setTextColor(Color.RED);*/holder.final_product_Price.setText(
            "Item Total : " + mContext.resources.getString(
                R.string.Rs
            ) + productsModel.getItemtotal()
        )
        holder.productMinus.setOnClickListener(View.OnClickListener {
            productClickListener.onMinusClick(
                productsModel
            )
        })
        holder.productPlus.setOnClickListener(View.OnClickListener {
            productClickListener.onPlusClick(
                productsModel
            )
        })

//        holder.btnFavourite.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                productClickListener.onWishListDialog(productsModel);
//            }
//        });
        holder.imgRemove.setOnClickListener(View.OnClickListener {
            productClickListener.onRemoveDialog(
                productsModel
            )
        })
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return productsModelList.size
    }

    init {
        this.productsModelList = productsModelList
        this.productClickListener = productClickListener
    }
}