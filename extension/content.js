document.addEventListener('mouseup', async () => {
    const selectedText = window.getSelection().toString().trim();
    if (!selectedText) return;

    chrome.runtime.sendMessage({ type: 'REPHRASE_TEXT', text: selectedText });
});