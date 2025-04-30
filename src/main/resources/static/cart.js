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

    fetch('/api/cart')
    .then(response => response.json())
    .then(cart => updateCart(cart))
    .catch(error => console.error('Error fetching cart:', error));

    document.querySelectorAll('.add-to-cart').forEach(button => {
        button.addEventListener('click', (e) => {
            const items = {
                id:button.dataset.id,
                item: button.dataset.item,
                price: parseFloat(button.dataset.price),
                quantity: 1
            };

            fetch('/api/cart/add', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(items)

            })
                .then(response => response.json())
                .then(cart => updateCart(cart))
                .catch(error => console.error('Error creating cart:', error));
        });
    });

    document.getElementById('cartItems').addEventListener('click', (e) => {
        if (e.target.classList.contains('remove-item')) {
            const id = e.target.dataset.id;
            fetch('/api/cart/remove/${id}', {
                method: 'DELETE'
            })
                .then(response => response.json())
                .then(cart => updateCart(cart))
                .catch(error => console.error('Error removing item:', error));
        }
    });

    document.getElementById('clearCart').addEventListener('click', () => {
        fetch('/api/cart/clear', {
            method: 'DELETE'
        })
            .then(response => response.json())
            .then(cart => updateCart(cart))
            .catch(error => console.error('Error clearing cart:', error));
    });
});
