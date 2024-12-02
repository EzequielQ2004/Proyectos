const lengthInput = document.getElementById('length');
const includeUppercase = document.getElementById('includeUppercase');
const includeNumbers = document.getElementById('includeNumbers');
const includeSymbols = document.getElementById('includeSymbols');
const generateButton = document.getElementById('generate');
const passwordOutput = document.getElementById('passwordOutput');

const getRandomLower = () => String.fromCharCode(Math.floor(Math.random() * 26) + 97);
const getRandomUpper = () => String.fromCharCode(Math.floor(Math.random() * 26) + 65);
const getRandomNumber = () => String.fromCharCode(Math.floor(Math.random() * 10) + 48);
const getRandomSymbol = () => {
    const symbols = '!@#$%^&*()_+[]{}<>?,.';
    return symbols[Math.floor(Math.random() * symbols.length)];
};

function generatePassword(length, options) {
    const availableFunctions = [];
    if (options.uppercase) availableFunctions.push(getRandomUpper);
    if (options.numbers) availableFunctions.push(getRandomNumber);
    if (options.symbols) availableFunctions.push(getRandomSymbol);
    availableFunctions.push(getRandomLower); // Siempre incluye minúsculas

    let password = '';
    for (let i = 0; i < length; i++) {
        const randomFunc = availableFunctions[Math.floor(Math.random() * availableFunctions.length)];
        password += randomFunc();
    }
    return password;
}

generateButton.addEventListener('click', () => {
    const length = +lengthInput.value;
    const options = {
        uppercase: includeUppercase.checked,
        numbers: includeNumbers.checked,
        symbols: includeSymbols.checked,
    };
    
    const password = generatePassword(length, options);
    passwordOutput.value = password;
});

const copyButton = document.getElementById('copy');
copyButton.addEventListener('click', () => {
    passwordOutput.select();
    document.execCommand('copy');
    alert('¡Contraseña copiada al portapapeles!');
});