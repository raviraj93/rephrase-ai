chrome.runtime.onMessage.addListener((message, sender, sendResponse) => {
    if (message.type === "REPHRASE_SELECTED_TEXT") {
        chrome.storage.sync.get(["backendUrl", "rephraseMode"], (config) => {
            fetch(`${config.backendUrl}/api/v1/rephrase?mode=${config.rephraseMode || "default"}`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({ text: message.text })
            })
                .then(res => res.json())
                .then(data => {
                    chrome.scripting.executeScript({
                        target: { tabId: message.tabId, allFrames: true },
                        func: (rephrased) => {
                            alert("Rephrased text: " + rephrased);
                        },
                        args: [data.rephrased]
                    });
                });
        });
    }
});
