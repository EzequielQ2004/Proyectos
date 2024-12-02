const todoForm = document.getElementById('todo-form');
const todoInput = document.getElementById('todo-input');
const todoList = document.getElementById('todo-list');

let tasks = JSON.parse(localStorage.getItem('tasks')) || [];

const saveTasks = () => {
    localStorage.setItem('tasks', JSON.stringify(tasks));
};

const renderTasks = () => {
    todoList.innerHTML = '';
    tasks.forEach((task, index) => {
        const li = document.createElement('li');
        li.className = `todo-item ${task.completed ? 'completed' : ''}`;

        li.innerHTML = `<span>${task.text}</span>
        <div>
            <button onclick="toggleComplete(${index})">✔</button>
            <button onclick="deleteTask(${index})">✖</button>
        </div>
        `;

        todoList.appendChild(li);
    });
};

const addTask = (text) => {
    tasks.push({ text, completed: false });
    saveTasks();
    renderTasks();
};

const toggleComplete = (index) => {
    tasks[index].completed = !tasks[index].completed;
    saveTasks();
    renderTasks();
};

const deleteTask = (index) => {
    tasks.splice(index, 1);
    saveTasks();
    renderTasks();
};

todoForm.addEventListener('submit', (e) => {
    e.preventDefault();
    const text = todoInput.value.trim();
    if (text) {
        addTask(text);
        todoInput.value = '';
    }
});

renderTasks();