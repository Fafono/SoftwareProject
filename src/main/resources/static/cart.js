document.addEventListener("DOMContentLoaded", function() {
    function  updateCart(cart){
        const cartItems = document.getElementById("cartItems");
        const cartTotal = document.getElementById("cartTotal");
        const cartCount = document.getElementById("cartCount");
        cartItems.innerHTML = '';
        let total = 0;
        let itemCount = 0;

        cart.forEach((item, index) => {
            const row = document.createElement("tr");
            row.innerHTML =
                <td>${item.item}</td>
                <td>R${parseFloat(item.price).toFixed(2)}</td>
                <td>${item.quantity}</td>
                <td>R${(item.price * item.quantity).toFixed(2)}</td>
                <td><button class="btn btn-danger btn-sm remove-item" data-id="${item.id}">Remove</button></td>;
            cartItems.appendChild(row);
            total += item.price * item.quantity;
            itemCount += item.quantity;
        });

        cartTotal.textContent = total.toFixed(2);
        cartCount.textContent = itemCount;
    }
})
