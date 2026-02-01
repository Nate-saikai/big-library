const API_BASE = "http://localhost:8080/api/orders";

async function addItem() {
    const bookId = document.getElementById("bookId").value;
    try {
        const res = await fetch(`${API_BASE}/addItem`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                Authorization: "Bearer " + localStorage.getItem("token")
            },
            body: JSON.stringify(bookId)
        });

        if (!res.ok) throw new Error(await res.text());

        const data = await res.json();
        alert("Added: " + data.book.bookName);
    } catch (err) {
        alert("Failed to add item: " + err.message);
    }
}

async function removeItem() {
    const bookId = document.getElementById("bookId").value;
    try {
        const res = await fetch(`${API_BASE}/deleteItem`, {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json",
                Authorization: "Bearer " + localStorage.getItem("token")
            },
            body: JSON.stringify(bookId)
        });

        if (!res.ok) throw new Error(await res.text());

        const data = await res.text();
        alert("Removed: " + data);
    } catch (err) {
        alert("Failed to remove item: " + err.message);
    }
}
