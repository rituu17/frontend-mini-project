document.getElementById("cart-modal").click(showCartModal())
// document.getElementById("cart-modal").click(fetchUserData())
function showCartModal() {
    // alert("something");
    // $('#cartModal').modal('show');


    // $.getJSON("http://localhost:3000/users", function (data) {

    //     $.each(data, function (key, value) {
    //         // console.log("data: ", data[0]);

    //     }
    //     );
    // });
    $.getJSON("http://localhost:3000/users", function (data) {

        $.each(data, function (key, value) {
            var cartItem = data[0].cart;
            // console.log("menu: ",cartItem);
            // console.log(data);
            fetchCartItem(cartItem);
        });
    });


    // fetchMenu();
}
function fetchMenu() {
    $.getJSON("http://localhost:3000/users", function (data) {

        $.each(data, function (key, value) {
            var cartItem = data[0].cart;
            // console.log("menu: ",cartItem);
            // console.log(data);
            fetchCartItem(cartItem);
        });
    });

    // <tbody id="t-body">
    //     <tr>
    //         <td class="w-25">
    //             <img src="media/album/album-desserts@4x.jpg"
    //                 class="img-fluid img-thumbnail" alt="">
    //         </td>
    //         <td>VEGGIE BURGER</td>
    //         <td>₹89</td>
    //         <td class="qty"><input type="text" class="form-control" id="input1" value="2"></td>
    //         <td>₹178</td>
    //         <td>
    //             <a href="#" class="btn btn-danger btn-sm">
    //                 <i class="fa fa-times"></i>
    //             </a>
    //         </td>
    //     </tr>
    // </tbody>

}
function fetchCartItem(cartItem) {
    // console.log("length", cartItem.length);
    if (cartItem.length > 0) {
        // console.log("siofhdfughsiudf");
        var menu;
        $.getJSON("http://localhost:3000/menu", function (menuData) {
            menu = menuData;
            // console.log("menu data: ", menu);
            // console.log("food items: ", foodItems);
            updateCart(cartItem, menu);
          



        });
        // var menu = fetchMenu();
        // console.log("menu data fetched: ", menu);


    }


}
function updateCart(cartItem, menu) {
    console.log("cart item: ", cartItem);
    console.log("menu item: ", menu);
    const DessertsItems = menu[1];
    const drinksItems = menu[2];
    // console.log("cart items: ", cartItem);
      for (var i = 0; i < cartItem.length; i++) {
        console.log(i);

        console.log(cartItem);
        
                var type = cartItem[i].type;
                var id = cartItem[i].id;
                var quantity = cartItem[i].quantity;
                console.log("this is food id: ", cartItem);
                if (type === "food") {
                    const foodItems = menu[0].food;
                    for (var i = 0; i < foodItems.length; i++) {
                        if (foodItems[i].id == id) {
                            console.log((i + 1)," ", id, "matched: ", foodItems[i]);

                        }
                        console.log(1);
                    }
                    console.log(2);
                    // console.log("food item length: ", foodItems.length);
                }
                console.log(3);
            }
}




// function fetchMenu() {
//     // var menu;
//     $.getJSON("http://localhost:3000/menu", function (menuData) {
//         return menuData;
//     });
//     // return menu;
// }
// function fetchUserData() {

//     $.getJSON("http://localhost:3000/users", function (data) {

//         $.each(data, function (key, value) {
//             console.log("data: ", data);
//         }
//         );
//     });
// }









