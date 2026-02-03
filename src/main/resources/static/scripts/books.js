async function loadBooks() {
    const valid = await checkSession();
    if (!valid) {
        alert("Session expired, please log in again.");
        window.location.href = "/login.html";
        return;
    }

    try {
        const res = await fetch("/api/books", { credentials: "include" });
        if (!res.ok) throw new Error(await res.text());

        const books = await res.json();
        const table = document.getElementById("booksTable");
        table.innerHTML = "<tr><th>ID</th><th>Name</th><th>Author</th><th>Action</th></tr>";

        books.forEach(b => {
            const row = table.insertRow();
            row.insertCell().textContent = b.bookId;
            row.insertCell().textContent = b.bookName;
            row.insertCell().textContent = b.author;

            const actionCell = row.insertCell();
            const btn = document.createElement("button");
            btn.textContent = "Add to Cart";
            btn.onclick = () => addItem(b.bookId);
            actionCell.appendChild(btn);
        });
    } catch (err) {
        alert("Failed to load books: " + err.message);
    }
}

async function addItem(bookId) {
    try {
        const res = await fetch("/api/orders/addItem", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            credentials: "include",
            body: JSON.stringify(bookId)
        });

        if (!res.ok) throw new Error(await res.text());
        const data = await res.json();
        alert("Added: " + data.book.bookName);
        updateCartCounter();
    } catch (err) {
        alert("Failed to add item: " + err.message);
    }
}

document.addEventListener("DOMContentLoaded", () => {
    loadNavbar();
    loadBooks();
});
