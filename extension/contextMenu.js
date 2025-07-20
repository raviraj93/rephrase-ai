chrome.runtime.onInstalled.addListener(() => {
    chrome.contextMenus.create({
        id: "rephrase-selection",
        title: "Rephrase with AI",
        contexts: ["selection"]
    });
});

chrome.contextMenus.onClicked.addListener((info, tab) => {
    if (info.menuItemId === "rephrase-selection" && info.selectionText) {
        chrome.runtime.sendMessage({
            type: "REPHRASE_SELECTED_TEXT",
            text: info.selectionText,
            tabId: tab.id
        });
    }
});