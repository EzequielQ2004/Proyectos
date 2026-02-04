// Estado del juego
const gameState = {
    board: Array(9).fill(''),
    currentPlayer: 'X',
    gameMode: 'pvp', //
    difficulty: 'medium',
    gameOver: false,
    scores: { X: 0, O: 0, draw: 0 },
    winningCombinations: [
        [0, 1, 2], [3, 4, 5], [6, 7, 8], 
        [0, 3, 6], [1, 4, 7], [2, 5, 8], 
        [0, 4, 8], [2, 4, 6]             
    ]
};

// Elementos del DOM
const boardElement = document.getElementById('board');
const statusElement = document.getElementById('status');
const scoreXElement = document.getElementById('score-x');
const scoreOElement = document.getElementById('score-o');
const scoreDrawElement = document.getElementById('score-draw');
const resetBtn = document.getElementById('reset-btn');
const newGameBtn = document.getElementById('new-game-btn');
const modePvPBtn = document.getElementById('mode-pvp');
const modePvCBtn = document.getElementById('mode-pvc');
const aiSettings = document.getElementById('ai-settings');
const difficultySelect = document.getElementById('difficulty');

// Inicializar el juego
function initGame() {
    createBoard();
    setupEventListeners();
    updateStatus();
    updateScores(); // Mostrar puntajes iniciales
}

// Crear el tablero
function createBoard() {
    boardElement.innerHTML = '';
    for (let i = 0; i < 9; i++) {
        const cell = document.createElement('div');
        cell.className = 'cell';
        cell.dataset.index = i;
        cell.addEventListener('click', () => handleCellClick(i));
        boardElement.appendChild(cell);
    }
}

// Configurar listeners
function setupEventListeners() {
    resetBtn.addEventListener('click', resetGame);
    newGameBtn.addEventListener('click', newGame);
    modePvPBtn.addEventListener('click', () => setGameMode('pvp'));
    modePvCBtn.addEventListener('click', () => setGameMode('pvc'));
    difficultySelect.addEventListener('change', (e) => {
        gameState.difficulty = e.target.value;
    });
}

// Manejar clic en celda
function handleCellClick(index) {
    // No hacer nada si el juego terminó o la celda ya está ocupada
    if (gameState.gameOver || gameState.board[index] !== '') {
        return;
    }

    // Realizar movimiento del jugador
    makeMove(index, gameState.currentPlayer);

    // Verificar si el juego terminó
    if (gameState.gameOver) {
        return;
    }

    // Si es modo vs IA y es turno de la IA
    if (gameState.gameMode === 'pvc' && gameState.currentPlayer === 'O') {
        setTimeout(makeAIMove, 600); // Pequeña pausa para mejor UX
    }
}

// Realizar movimiento
function makeMove(index, player) {
    gameState.board[index] = player;
    renderBoard();

    // Verificar ganador
    if (checkWinner(player)) {
        gameState.gameOver = true;
        gameState.scores[player]++;
        updateScores();
        showWinner(player);
        return;
    }

    // Verificar empate
    if (isBoardFull()) {
        gameState.gameOver = true;
        gameState.scores.draw++;
        updateScores();
        showDraw();
        return;
    }

    // Cambiar turno
    gameState.currentPlayer = gameState.currentPlayer === 'X' ? 'O' : 'X';
    updateStatus();
}

// Renderizar el tablero
function renderBoard() {
    const cells = document.querySelectorAll('.cell');
    cells.forEach((cell, index) => {
        cell.textContent = gameState.board[index];
        cell.className = 'cell'; // Resetear clases para quitar efectos de ganador
        if (gameState.board[index] === 'X') {
            cell.classList.add('x');
        } else if (gameState.board[index] === 'O') {
            cell.classList.add('o');
        }
    });
}

// Verificar si hay ganador
function checkWinner(player) {
    return gameState.winningCombinations.some(combination => {
        return combination.every(index => gameState.board[index] === player);
    });
}

// Marcar celdas ganadoras
function markWinningCells(player) {
    const winningCombo = gameState.winningCombinations.find(combination => {
        return combination.every(index => gameState.board[index] === player);
    });

    if (winningCombo) {
        winningCombo.forEach(index => {
            document.querySelector(`.cell[data-index="${index}"]`).classList.add('win');
        });
    }
}

// Verificar si el tablero está lleno
function isBoardFull() {
    return gameState.board.every(cell => cell !== '');
}

// Actualizar estado
function updateStatus() {
    const playerName = gameState.currentPlayer === 'X' ? 'Jugador X' : 
                      (gameState.gameMode === 'pvc' ? 'IA (O)' : 'Jugador O');
    const icon = gameState.currentPlayer === 'X' ? 'fa-xmark' : 'fa-o';
    
    statusElement.innerHTML = `
        <i class="fas ${icon}"></i> Turno de <span class="player-name">${playerName}</span>
    `;
}

// Actualizar marcador
function updateScores() {
    scoreXElement.textContent = gameState.scores.X;
    scoreOElement.textContent = gameState.scores.O;
    scoreDrawElement.textContent = gameState.scores.draw;
}

// Mostrar ganador
function showWinner(player) {
    const winnerName = player === 'X' ? 'Jugador X' : 
                      (gameState.gameMode === 'pvc' ? 'IA' : 'Jugador O');
    statusElement.innerHTML = `
        <i class="fas fa-trophy" style="color: gold;"></i> 
        <span style="color: #27ae60; font-weight: 700;">${winnerName} gana!</span>
    `;
    markWinningCells(player);
}

// Mostrar empate
function showDraw() {
    statusElement.innerHTML = `
        <i class="fas fa-balance-scale"></i> 
        <span style="color: #95a5a6; font-weight: 700;">¡Empate!</span>
    `;
}

// IA - Movimiento
function makeAIMove() {
    if (gameState.gameOver) return;

    let index;
    switch (gameState.difficulty) {
        case 'easy':
            index = aiEasy();
            break;
        case 'medium':
            index = aiMedium();
            break;
        case 'hard':
            index = aiHard();
            break;
    }

    if (index !== null) {
        makeMove(index, 'O');
    }
}

// IA Fácil: Movimiento aleatorio
function aiEasy() {
    const emptyCells = gameState.board
        .map((cell, index) => cell === '' ? index : null)
        .filter(index => index !== null);
    
    if (emptyCells.length === 0) return null;
    return emptyCells[Math.floor(Math.random() * emptyCells.length)];
}

// IA Media: Bloquea y gana
function aiMedium() {
    // Primero intenta ganar
    const winMove = findWinningMove('O');
    if (winMove !== null) return winMove;

    // Luego bloquea al jugador
    const blockMove = findWinningMove('X');
    if (blockMove !== null) return blockMove;

    // Sino, movimiento aleatorio
    return aiEasy();
}

// IA Difícil: Minimax simplificado
function aiHard() {
    // Primero intenta ganar
    const winMove = findWinningMove('O');
    if (winMove !== null) return winMove;

    // Luego bloquea al jugador
    const blockMove = findWinningMove('X');
    if (blockMove !== null) return blockMove;

    // Estrategia: tomar centro si está disponible
    if (gameState.board[4] === '') return 4;

    // Estrategia: tomar esquinas
    const corners = [0, 2, 6, 8].filter(i => gameState.board[i] === '');
    if (corners.length > 0) {
        return corners[Math.floor(Math.random() * corners.length)];
    }

    // Sino, movimiento aleatorio
    return aiEasy();
}

// Buscar movimiento ganador
function findWinningMove(player) {
    for (let i = 0; i < 9; i++) {
        if (gameState.board[i] === '') {
            // Simular movimiento
            gameState.board[i] = player;
            const isWinning = checkWinner(player);
            gameState.board[i] = ''; // Deshacer

            if (isWinning) return i;
        }
    }
    return null;
}

// Cambiar modo de juego
function setGameMode(mode) {
    gameState.gameMode = mode;
    
    // Actualizar botones activos
    modePvPBtn.classList.toggle('active', mode === 'pvp');
    modePvCBtn.classList.toggle('active', mode === 'pvc');
    
    // Mostrar/ocultar configuración IA
    aiSettings.style.display = mode === 'pvc' ? 'block' : 'none';
    
    // Reiniciar juego al cambiar modo
    newGame();
}

// Reiniciar juego actual
function resetGame() {
    gameState.board = Array(9).fill('');
    gameState.currentPlayer = 'X';
    gameState.gameOver = false;
    renderBoard();
    updateStatus();
}

// Nuevo juego 
function newGame() {
    // Reiniciar tablero y estado
    gameState.board = Array(9).fill('');
    gameState.currentPlayer = 'X';
    gameState.gameOver = false;
    
    // Reiniciar puntajes a cero
    gameState.scores.X = 0;
    gameState.scores.O = 0;
    gameState.scores.draw = 0;
    updateScores();
    
    // Renderizar
    renderBoard();
    updateStatus();
    
    // Feedback visual
    statusElement.innerHTML = `
        <i class="fas fa-gamepad"></i> 
        <span>Nuevo juego iniciado</span>
    `;
    setTimeout(updateStatus, 1500);
}

// Iniciar el juego cuando se carga la página
document.addEventListener('DOMContentLoaded', initGame);