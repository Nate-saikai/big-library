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
        table.innerHTML = "<tr><th>ID</th><th>Name</th><th>Author</th><th>Quantity</th><th>Action</th></tr>";

        items.forEach(item => {
            const row = table.insertRow();
            row.insertCell().textContent = item.bookCode;
            row.insertCell().textContent = item.bookName;
            row.insertCell().textContent = item.author;
            row.insertCell().textContent = item.quantity;

            const actionCell = row.insertCell();
            const btn = document.createElement("button");
            btn.textContent = "Remove";
            btn.onclick = () => removeItem(item.bookId);
            actionCell.appendChild(btn);
        });
    } catch (err) {
        alert("Failed to load cart: " + err.message);
    }
}

async function removeItem(bookId) {
    try {
        const res = await fetch("/api/orders/deleteItem", {
            method: "DELETE",
            headers: { "Content-Type": "application/json" },
            credentials: "include",
            body: JSON.stringify(bookId)
        });

        if (!res.ok) throw new Error(await res.text());
        const data = await res.text();
        alert("Removed: " + data);
        //await updateCartCounter();
        await loadCart(); // refresh cart
    } catch (err) {
        alert("Failed to remove item: " + err.message);
    }
}

async function checkout() {
    try {
        const res = await fetch("/api/orders/checkout", {
            method: "POST",
            credentials: "include"
        });

        if (!res.ok) throw new Error(await res.text());

        const items = await res.json();
        const table = document.getElementById("booksTable");
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
    loadCart();
});
