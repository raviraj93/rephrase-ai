document.addEventListener("mouseup", () => {
    const selection = window.getSelection().toString().trim();
    if (selection.length > 0) {
        chrome.runtime.sendMessage({ type: "REPHRASE_SELECTED_TEXT", text: selection }, (response) => {
            console.log("Response:", response);
        });
    }
});