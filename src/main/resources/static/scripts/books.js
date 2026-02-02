// books.js

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
        table.innerHTML = "<tr><th>ID</th><th>Name</th><th>Author</th></tr>";

        books.forEach(b => {
            const row = table.insertRow();
            row.insertCell().textContent = b.bookId;
            row.insertCell().textContent = b.bookName;
            row.insertCell().textContent = b.author;
        });
    } catch (err) {
        alert("Failed to load books: " + err.message);
    }
}

document.addEventListener("DOMContentLoaded", () => {
    loadNavbar();
    loadBooks();
});
