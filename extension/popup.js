document.getElementById('rephraseBtn').addEventListener('click', async () => {
    const inputText = document.getElementById('inputText').value;
    const resultEl = document.getElementById('result');

    if (!inputText.trim()) {
        resultEl.textContent = 'Please enter some text.';
        return;
    }

    resultEl.textContent = 'Rephrasing...';

    const response = await fetch('http://localhost:8080/api/v1/rephrase', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ text: inputText })
    });

    if (!response.ok) {
        resultEl.textContent = 'Error contacting the server.';
        return;
    }

    const data = await response.json();
    resultEl.textContent = data.rephrased;
});