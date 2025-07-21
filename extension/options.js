document.getElementById("save").addEventListener("click", () => {
    const backendUrl = document.getElementById("backendUrl").value;
    const rephraseMode = document.getElementById("rephraseMode").value;
    chrome.storage.sync.set({ backendUrl, rephraseMode }, () => {
        alert("Settings saved!");
    });
});