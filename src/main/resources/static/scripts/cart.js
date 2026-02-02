// cart.js

async function loadCart() {
    const valid = await checkSession();
    if (!valid) {
        alert("Session expired, please log in again.");
        window.location.href = "/login.html";
        return;
    }

    try {
        const res = await fetch("/api/orders/cart", { credentials: "include" });
        if (!res.ok) throw new Error(await res.text());

        const items = await res.json();
        const table = document.getElementById("booksTable");
        table.innerHTML = "<tr><th>ID</th><th>Name</th><th>Author</th><th>Quantity</th></tr>";

        items.forEach(item => {
            const row = table.insertRow();
            row.insertCell().textContent = item.book.bookId;
            row.insertCell().textContent = item.book.bookName;
            row.insertCell().textContent = item.book.author;
            row.insertCell().textContent = item.quantity;
        });
    } catch (err) {
        alert("Failed to load cart: " + err.message);
    }
}

document.addEventListener("DOMContentLoaded", () => {
    loadNavbar();
    loadCart();
});
