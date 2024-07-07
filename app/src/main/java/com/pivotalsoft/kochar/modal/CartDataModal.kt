package com.pivotalsoft.kochar.modal

import java.io.Serializable

data class CartDataModal(var photo: String,
                         var productdes: String,
                        // var productunit: String,
                         var actual_price: String,
                         var producttotalprice: String,
                         var userid: String,
                         var no_units: String,
                         var product_id: String,
                         var cartitemid: String,
                         var added_on: String)