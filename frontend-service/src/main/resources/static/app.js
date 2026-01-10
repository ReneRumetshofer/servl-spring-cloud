const API_BASE_URL = 'http://localhost:8080/api';

let cart = [];

document.addEventListener('DOMContentLoaded', () => {
    loadProducts();
    loadOrders();

    document.getElementById('place-order-btn').addEventListener('click', placeOrder);
    document.getElementById('clear-cart-btn').addEventListener('click', clearCart);
    document.getElementById('refresh-orders-btn').addEventListener('click', loadOrders);
});

async function loadProducts() {
    const productsList = document.getElementById('products-list');

    try {
        const response = await fetch(`${API_BASE_URL}/products`);
        if (!response.ok) throw new Error('Failed to load products');

        const products = await response.json();

        if (products.length === 0) {
            productsList.innerHTML = '<p class="empty">No products available</p>';
            return;
        }

        productsList.innerHTML = products.map(product => `
            <div class="product-card">
                <h3>${escapeHtml(product.name)}</h3>
                <p class="description">${escapeHtml(product.description)}</p>
                <p class="price">$${product.price.toFixed(2)}</p>
                <button onclick="addToCart('${product.id}', '${escapeHtml(product.name)}')">
                    Add to Cart
                </button>
            </div>
        `).join('');
    } catch (error) {
        productsList.innerHTML = `<p class="error">Error loading products: ${error.message}</p>`;
    }
}

function addToCart(productId, productName) {
    cart.push({ productId, productName });
    renderCart();
}

function removeFromCart(index) {
    cart.splice(index, 1);
    renderCart();
}

function clearCart() {
    cart = [];
    renderCart();
}

function renderCart() {
    const cartItems = document.getElementById('cart-items');
    const placeOrderBtn = document.getElementById('place-order-btn');

    if (cart.length === 0) {
        cartItems.innerHTML = '<p class="empty-cart">Your cart is empty</p>';
        placeOrderBtn.disabled = true;
        return;
    }

    cartItems.innerHTML = cart.map((item, index) => `
        <div class="cart-item">
            <span>${escapeHtml(item.productName)}</span>
            <button onclick="removeFromCart(${index})">Remove</button>
        </div>
    `).join('');

    placeOrderBtn.disabled = false;
}

async function placeOrder() {
    if (cart.length === 0) return;

    const placeOrderBtn = document.getElementById('place-order-btn');
    placeOrderBtn.disabled = true;
    placeOrderBtn.textContent = 'Placing Order...';

    try {
        const response = await fetch(`${API_BASE_URL}/orders`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                productIds: cart.map(item => item.productId)
            })
        });

        if (!response.ok) throw new Error('Failed to place order');

        cart = [];
        renderCart();
        loadOrders();
        alert('Order placed successfully!');
    } catch (error) {
        alert(`Error placing order: ${error.message}`);
    } finally {
        placeOrderBtn.textContent = 'Place Order';
        placeOrderBtn.disabled = cart.length === 0;
    }
}

async function loadOrders() {
    const ordersList = document.getElementById('orders-list');

    try {
        const response = await fetch(`${API_BASE_URL}/orders`);
        if (!response.ok) throw new Error('Failed to load orders');

        const orders = await response.json();

        if (orders.length === 0) {
            ordersList.innerHTML = '<p class="no-orders">No orders yet</p>';
            return;
        }

        ordersList.innerHTML = orders.map(order => `
            <div class="order-card">
                <h4>Order #${order.id.substring(0, 8)}...</h4>
                <p class="order-date">${new Date(order.createdAt).toLocaleString()}</p>
                <ul>
                    ${order.items.map(item => `
                        <li>${escapeHtml(item.productName)} (ID: ${item.productId})</li>
                    `).join('')}
                </ul>
            </div>
        `).join('');
    } catch (error) {
        ordersList.innerHTML = `<p class="error">Error loading orders: ${error.message}</p>`;
    }
}

function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}
