// auth.js

document.getElementById("registerForm")?.addEventListener("submit", async (e) => {
    e.preventDefault();
    const username = document.getElementById("regUsername").value;
    const password = document.getElementById("regPassword").value;

    try {
        const res = await fetch("/api/user/register", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            credentials: "include",
            body: JSON.stringify({ username, password })
        });

        if (!res.ok) throw new Error(await res.text());
        alert("Registration successful! You can now log in.");
    } catch (err) {
        alert("Registration failed: " + err.message);
    }
});
