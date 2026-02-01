const API_BASE = "http://localhost:8080/api/books";

async function loadBooks() {
    try {
        const res = await fetch(API_BASE, {
            headers: { Authorization: "Bearer " + localStorage.getItem("token") }
        });

        if (!res.ok) throw new Error(await res.text());

        const books = await res.json();
        const table = document.getElementById("booksTable");

        // Clear old rows
        table.innerHTML = "<tr><th>ID</th><th>Name</th><th>Author</th></tr>";

        books.forEach(b => {
            const row = table.insertRow();
            row.insertCell().textContent = b.bookId;
            row.insertCell().textContent = b.bookName;
            row.insertCell().textContent = b.author;
        });

        // Example: Export to CSV
        // TODO: implement CSV export if needed
    } catch (err) {
        alert("Failed to load books: " + err.message);
    }
}
