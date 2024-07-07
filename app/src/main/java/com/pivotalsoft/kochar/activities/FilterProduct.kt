package com.pivotalsoft.kochar.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.pivotalsoft.kochar.R
import com.pivotalsoft.kochar.Utilities.hideKeyboard
import com.pivotalsoft.kochar.Utilities.snackbar
import com.pivotalsoft.kochar.adapters.SearchAdapter
import com.pivotalsoft.kochar.api.RetrofitClient
import com.pivotalsoft.kochar.databinding.ActivityFilterProductBinding
import com.pivotalsoft.kochar.response.SchoolDataResponse
import com.pivotalsoft.kochar.userdetails.SchoolData
import com.pivotalsoft.kochar.viewModel.CartViewModel
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FilterProduct : AppCompatActivity() {
    private lateinit var binding: ActivityFilterProductBinding
    private var dataList: ArrayList<SchoolData> = arrayListOf()
    private var schoolNames: ArrayList<String> = arrayListOf()
    private lateinit var searchAdapter: SearchAdapter
    var _schoolname: String? = null
    var classint:Int? = null
    var _classint: Int? = null
    var genderint: Int? = null
    var _genderint: Int? = null

    private var selectedPlant: SchoolData = SchoolData("","","",
        "","","","")
    private var _plantId = 0


    init {
        getCharacterInfo()
    }


    private fun getCharacterInfo() {
        GlobalScope.launch(Dispatchers.IO) {
                         try {
                            // dataList.clear()
                             RetrofitClient.instance.getSchoolDetails()
                                 .enqueue(object : Callback<SchoolDataResponse>{
                                     override fun onResponse(call: Call<SchoolDataResponse>, response: Response<SchoolDataResponse>) {
                                         val det = response.body()
                                         if (response.isSuccessful){
                                             binding.nsv1.snackbar("DATA Getted")
                                             if (det != null) {
//                                for (i in 0 until det.schooldata.size){
//
//                                    dataList.addAll(listOf(det.schooldata[i]))
//                                    //dataList.addAll(det.schooldata[i])
//                                }
                                                 for (i in det?.schooldata.indices){
                                                     val items = SchoolData(det?.schooldata[i].schoolid,
                                                         det?.schooldata[i].fullname,
                                                         det?.schooldata[i].city,
                                                         det?.schooldata[i].address,
                                                         det?.schooldata[i].contactname,
                                                         det?.schooldata[i].contactno,
                                                         det?.schooldata[i].designation
                                                     )
                                                     dataList.addAll(listOf(items))
                                                   val schoolNamess = dataList[i].fullname

                                                     val schid = dataList[i].schoolid
                                                     schoolNames.addAll(listOf(schoolNamess))

                      //   Toast.makeText(applicationContext, "name is $schoolNames", Toast.LENGTH_LONG).show()

                                                 }
                                             }
                                         }
                                     }
                                     override fun onFailure(call: Call<SchoolDataResponse>, t: Throwable) {
                                         binding.nsv1.snackbar("Please Check The Internet Connection")
                                         binding.ddschool.inputType = InputType.TYPE_NULL
                                     }

                                 })
                         }
                         catch (e:Exception) {
                             withContext(Dispatchers.Main) {
                                 Toast.makeText(applicationContext, "Cannot Load Data", Toast.LENGTH_LONG).show()
                             }
                         }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilterProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Search Uniform"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val ddschool: AutoCompleteTextView = findViewById(R.id.ddschool)
        val ddschoolres = resources.getStringArray(R.array.school)
        searchAdapter = SearchAdapter(this,R.layout.support_simple_spinner_dropdown_item,dataList)
        val ddschooladapter = ArrayAdapter(this, R.layout.dropdown_itemlist, schoolNames)
        ddschool.setAdapter(searchAdapter)

        ddschool.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchAdapter.filter.filter(s) // if adapter changed here adapter name should be change
                binding.tvschooladdress.text = ""
            }
            override fun afterTextChanged(s: Editable?) {

            }
        })
        ddschool.onItemClickListener =
            OnItemClickListener { adapterView, view, position, id ->
                val selectedPoi = adapterView.adapter.getItem(position) as SchoolData?
//                var strs = adapterView.adapter.getItem(position)
//               var strss: String = adapterView.getPositionForView(view).toString()
//                var st = adapterView.getItemAtPosition(position).toString()
                binding.tvschooladdress.visibility = View.VISIBLE
                binding.tvschooladdress.text = selectedPoi?.address
                _schoolname = selectedPoi?.fullname
               // Toast.makeText(applicationContext,"data is ${selectedPoi?.schoolid}",Toast.LENGTH_SHORT).show()
                binding.nsv1.hideKeyboard()
            }
///////////////////////////////////////////////////////////////////////////

        var classs = listOf<String>("1st class","2nd class","3rd class")
        val ddclassres = resources.getStringArray(R.array.classs)
        val ddclassadapter = ArrayAdapter(this, R.layout.dropdown_itemlist, classs)
        binding.ddclass.setAdapter(ddclassadapter)

        binding.ddclass.onItemClickListener =
            OnItemClickListener { adapterView, view, position, id ->
                var strs = adapterView?.getItemAtPosition(position).toString()
                when(strs){
                    "1st class" -> {
                        classint = 1
                        _classint = classint
                     //   Toast.makeText(this@FilterProduct, "you choosen ${classint}", Toast.LENGTH_SHORT).show()

                    }
                    "2nd class" -> {
                        classint = 2
                        _classint = classint
                      //  Toast.makeText(this@FilterProduct, "you choosen ${classint}", Toast.LENGTH_SHORT).show()

                    }
                    "3rd class" -> {
                        classint = 3
                        _classint = classint
                      //  Toast.makeText(this@FilterProduct, "you choosen ${classint}", Toast.LENGTH_SHORT).show()

                    }
                    else -> {
                       // Toast.makeText(this@FilterProduct, "Select from one of them", Toast.LENGTH_SHORT).show()

                    }
                }
              //  Toast.makeText(this@FilterProduct, "you choosen ${strs}", Toast.LENGTH_SHORT).show()
                binding.nsv1.hideKeyboard()
            }
/////////////////////////////////////////////////////////////
        var ddgenderres = listOf<String>("Male","Female")
        val ddgenderadapter = ArrayAdapter(this, R.layout.dropdown_itemlist, ddgenderres)
        binding.ddgender.setAdapter(ddgenderadapter)


        binding.ddgender.onItemClickListener =
            OnItemClickListener { adapterView, view, position, id ->
                var strs = adapterView?.getItemAtPosition(position)
                when (strs) {
                    "Male" -> {
                        genderint = 1
                        _genderint = genderint
                       // Toast.makeText(this@FilterProduct, "you choosen ${genderint}", Toast.LENGTH_SHORT).show()
                        binding.nsv1.hideKeyboard()

                    }
                    "Female" -> {
                        genderint = 0
                        _genderint = genderint
                       // Toast.makeText(this@FilterProduct, "you choosen ${genderint}", Toast.LENGTH_SHORT).show()
                        binding.nsv1.hideKeyboard()

                    }
                    //  Toast.makeText(this@FilterProduct, "you choosen ${strs}", Toast.LENGTH_SHORT).show()

                    else -> {
                        binding.nsv1.hideKeyboard()
                    }
                }
            }


////////////////////////////////////////////////////////////////////////////////
        binding.btsearch.setOnClickListener {
            if (binding.ddschool.text.toString().trim().isEmpty()){
                binding.ddschool.error = "Select School or College Name"
                binding.ddschool.requestFocus()
                return@setOnClickListener

            }

            if (binding.ddclass.text.toString().trim().isEmpty()){
                binding.ddclass.error = "Select Class"
                binding.ddclass.requestFocus()
                return@setOnClickListener

            }

            if (binding.ddgender.text.toString().trim().isEmpty()){
                binding.ddgender.error = "Select Gender"
                binding.ddgender.requestFocus()
                return@setOnClickListener

            }

//            Toast.makeText(this@FilterProduct, "you school $_schoolname", Toast.LENGTH_SHORT).show()
//
//            Toast.makeText(this@FilterProduct, "you class $_classint", Toast.LENGTH_SHORT).show()
//
//            Toast.makeText(this@FilterProduct, "you gender $_genderint", Toast.LENGTH_SHORT).show()

            val i = Intent(this, TabSubOneActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            i.putExtra("school_name",_schoolname.toString())
            i.putExtra("class_name",_classint)
            i.putExtra("gender_name",_genderint)
            startActivity(i)

        }


//        (textInputLayout.getEditText() as AutoCompleteTextView).onItemClickListener =
//            OnItemClickListener { adapterView, view, position, id ->
//                val selectedValue: String = arrayAdapter.getItem(position)
//            }
    }

//     fun fetchComments() {
//
//        val commentFetchjob = Job()
//        val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
//            throwable.printStackTrace()
//            Toast.makeText(applicationContext,"Error Occured",Toast.LENGTH_SHORT).show()
//
//        }
//        val scope = CoroutineScope(commentFetchjob + Dispatchers.IO)
//        scope.launch(errorHandler){
//            // fetch data
//
//
//
//
//
//
//        }
//
//    }




    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> NavUtils.navigateUpFromSameTask(this)
        }
        return super.onOptionsItemSelected(item)
    }


}