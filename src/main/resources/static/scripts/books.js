let currentPage = 0;
const pageSize = 10;

async function loadCategories() {
    try {
        const res = await fetch("/api/books/category/list", { credentials: "include" });
        if (!res.ok) throw new Error(await res.text());

        const categories = await res.json();
        const select = document.getElementById("categorySelect");

        categories.forEach(cat => {
            const option = document.createElement("option");
            option.value = cat.categoryName; // adjust field names to your DTO
            option.textContent = cat.categoryName;
            select.appendChild(option);
        });
    } catch (err) {
        console.error("Failed to load categories:", err.message);
    }
}

async function loadBooks(page = 0, categoryName = "") {
    try {
        const url = categoryName
            ? `/api/books/category?page=${page}&size=${pageSize}&category=${categoryName}`
            : `/api/books?page=${page}&size=${pageSize}`;

        const res = await fetch(url, { credentials: "include" });
        if (!res.ok) throw new Error(await res.text());

        const data = await res.json();
        const books = data.content || data; // adjust depending on backend response
        const table = document.getElementById("booksTable");
        table.innerHTML = "<tr><th>Title</th><th>Author</th><th>Category</th><th>Description</th><th>price</th><th></th></tr>";

        books.forEach(b => {
            const row = table.insertRow();
            row.insertCell().textContent = b.bookName;
            row.insertCell().textContent = b.author;
            row.insertCell().textContent = b.category;
            row.insertCell().textContent = b.description;
            row.insertCell().textContent = b.price;

            const actionCell = row.insertCell();
            const btn = document.createElement("button");
            btn.textContent = "Add to Cart";
            btn.onclick = () => addItem(b.bookId); // <-- uses addItem
            actionCell.appendChild(btn);
        });

        if (data.totalPages) renderPagination(data.totalPages, page, categoryId);
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

function renderPagination(totalPages, page, categoryId) {
    const nav = document.getElementById("pagination");
    nav.innerHTML = "";

    if (page > 0) {
        const prev = document.createElement("button");
        prev.textContent = "Prev";
        prev.onclick = () => loadBooks(page - 1, categoryId);
        nav.appendChild(prev);
    }

    if (page < totalPages - 1) {
        const next = document.createElement("button");
        next.textContent = "Next";
        next.onclick = () => loadBooks(page + 1, categoryId);
        nav.appendChild(next);
    }
}

document.addEventListener("DOMContentLoaded", () => {
    loadNavbar();
    loadCategories();
    loadBooks();

    document.getElementById("filterBtn").addEventListener("click", () => {
        let categoryName = document.getElementById("categorySelect").value;
        loadBooks(0, categoryName);
    });
});
