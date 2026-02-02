// orders.js

const API_BASE = "/api/orders";

async function addItem() {
    try {
        const res = await fetch(`${API_BASE}/addItem`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            credentials: "include",
            body: JSON.stringify(document.getElementById("bookId").value)
        });

        if (!res.ok) throw new Error(await res.text());
        const data = await res.json();
        alert("Added: " + data.book.bookName);
        updateCartCounter();
    } catch (err) {
        alert("Failed to add item: " + err.message);
    }
}

async function removeItem() {
    try {
        const res = await fetch(`${API_BASE}/deleteItem`, {
            method: "DELETE",
            headers: { "Content-Type": "application/json" },
            credentials: "include",
            body: JSON.stringify(document.getElementById("bookId").value)
        });

        if (!res.ok) throw new Error(await res.text());
        const data = await res.text();
        alert("Removed: " + data);
        updateCartCounter();
    } catch (err) {
        alert("Failed to remove item: " + err.message);
    }
}

async function checkout() {
    try {
        const res = await fetch(`${API_BASE}/checkout`, {
            method: "POST",
            credentials: "include"
        });

        if (!res.ok) throw new Error(await res.text());

        const items = await res.json();
        const table = document.getElementById("checkoutTable");
        table.innerHTML = `
          <tr>
            <th>Book ID</th>
            <th>Title</th>
            <th>Author</th>
            <th>Quantity</th>
          </tr>
        `;

        items.forEach(item => {
            const row = table.insertRow();
            row.insertCell().textContent = item.book.bookId;
            row.insertCell().textContent = item.book.bookName;
            row.insertCell().textContent = item.book.author;
            row.insertCell().textContent = item.quantity;
        });

        alert("Checkout complete!");
        updateCartCounter();
    } catch (err) {
        alert("Checkout failed: " + err.message);
    }
}

document.addEventListener("DOMContentLoaded", () => {
    loadNavbar();
});
