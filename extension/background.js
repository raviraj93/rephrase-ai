const BACKEND_URL = "https://rephrase-ai.onrender.com"

chrome.runtime.onInstalled.addListener(() => {
    chrome.contextMenus.create({
        id: "rephrase-selection",
        title: "Rephrase this text",
        contexts: ["selection"]
    });
});

chrome.contextMenus.onClicked.addListener((info, tab) => {
    if (info.menuItemId === "rephrase-selection") {
        chrome.storage.sync.get(["rephraseMode"], (config) => {
            fetch(`${BACKEND_URL}/api/v1/rephrase?mode=${config.rephraseMode || "default"}`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ text: info.selectionText })
            })
                .then(res => res.json())
                .then(data => {
                    chrome.scripting.executeScript({
                        target: { tabId: tab.id },
                        func: (rephrased) => alert("Rephrased: " + rephrased),
                        args: [data.rephrased]
                    });
                })
                .catch(err => console.error("Rephrase error:", err));
        });
    }
});