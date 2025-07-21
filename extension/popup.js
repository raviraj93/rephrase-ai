const BACKEND_URL = "https://rephrase-ai.onrender.com";

document.getElementById("rephraseButton").addEventListener("click", () => {
    const text = document.getElementById("inputText").value;
    const mode = document.querySelector("input[name=mode]:checked").value;

    fetch(`${BACKEND_URL}/api/v1/rephrase?mode=${mode}`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ text })
    })
        .then(res => res.json())
        .then(data => {
            document.getElementById("result").innerText = data.rephrased;
        })
        .catch(err => {
            document.getElementById("result").innerText = "Error: " + err.message;
        });
});