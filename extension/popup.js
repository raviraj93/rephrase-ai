document.getElementById("rephraseButton").addEventListener("click", () => {
    const text = document.getElementById("inputText").value;
    chrome.storage.sync.get(["backendUrl", "rephraseMode"], (config) => {
        fetch(`${config.backendUrl}/api/v1/rephrase?mode=${config.rephraseMode || "default"}`, {
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
});