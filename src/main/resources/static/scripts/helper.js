// helpers.js

// Load navbar fragment
async function loadNavbar() {
    try {
        const res = await fetch("/fragments/navbar.html");
        if (!res.ok) throw new Error("Failed to load navbar");
        const html = await res.text();
        document.getElementById("navbar").innerHTML = html;
        updateCartCounter();
    } catch (err) {
        console.error(err);
    }
}

// Update cart counter
async function updateCartCounter() {
    try {
        const res = await fetch("/api/orders/cartSize", { credentials: "include" });
        if (!res.ok) return;
        const count = await res.json();
        const counter = document.getElementById("cartCounter");
        if (counter) counter.textContent = `Cart: ${count}`;
    } catch (err) {
        console.error("Cart counter update failed:", err);
    }
}

// Check if session is valid
async function checkSession() {
    try {
        const res = await fetch("/api/user/me", { credentials: "include" });
        return res.ok;
    } catch {
        return false;
    }
}
