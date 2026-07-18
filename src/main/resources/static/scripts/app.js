const STORAGE_KEY = "linkhub.records";

const form = document.getElementById("link-form");
const editForm = document.getElementById("edit-form");
const list = document.getElementById("link-list");
const emptyState = document.getElementById("empty-state");
const titleInput = document.getElementById("link-title");
const urlInput = document.getElementById("link-url");
const tagsInput = document.getElementById("link-tags");
const editIdInput = document.getElementById("edit-id");
const editTitleInput = document.getElementById("edit-title");
const editUrlInput = document.getElementById("edit-url");
const editTagsInput = document.getElementById("edit-tags");

let records = loadRecords();

function loadRecords() {
    try {
        return JSON.parse(localStorage.getItem(STORAGE_KEY)) || [];
    } catch {
        return [];
    }
}

function saveRecords() {
    localStorage.setItem(STORAGE_KEY, JSON.stringify(records));
}

function formatTime(iso) {
    const d = new Date(iso);
    const pad = (n) => String(n).padStart(2, "0");
    return `${d.getFullYear()}-${pad(d.getMonth() + 1)}-${pad(d.getDate())} ${pad(d.getHours())}:${pad(d.getMinutes())}`;
}

function render() {
    list.innerHTML = "";
    emptyState.style.display = records.length ? "none" : "block";

    records.forEach((record) => {
        const li = document.createElement("li");
        li.className = "link-card card mb-3";

        const tagsHtml = record.tags
            .map((t) => `<span class="badge tag-badge">${escapeHtml(t)}</span>`)
            .join("");

        li.innerHTML = `
            <div class="card-body d-flex justify-content-between align-items-start">
                <div class="link-info">
                    <a href="${escapeHtml(record.url)}" target="_blank" rel="noopener" class="link-title">${escapeHtml(record.title)}</a>
                    <div class="link-meta">${formatTime(record.createdAt)}</div>
                    <div class="link-tags mt-2">${tagsHtml}</div>
                </div>
                <div class="link-actions">
                    <button class="btn btn-sm btn-outline-secondary me-2" data-action="edit" data-id="${record.id}">編輯</button>
                    <button class="btn btn-sm btn-outline-danger" data-action="delete" data-id="${record.id}">刪除</button>
                </div>
            </div>
        `;
        list.appendChild(li);
    });
}

function escapeHtml(str) {
    const div = document.createElement("div");
    div.textContent = str;
    return div.innerHTML;
}

form.addEventListener("submit", (e) => {
    e.preventDefault();
    const record = {
        id: crypto.randomUUID(),
        title: titleInput.value.trim(),
        url: urlInput.value.trim(),
        tags: tagsInput.value.split(",").map((t) => t.trim()).filter(Boolean),
        createdAt: new Date().toISOString(),
    };
    records.unshift(record);
    saveRecords();
    render();
    form.reset();
    bootstrap.Modal.getInstance(document.getElementById("addModal")).hide();
});

list.addEventListener("click", (e) => {
    const btn = e.target.closest("button[data-action]");
    if (!btn) return;
    const id = btn.dataset.id;
    const action = btn.dataset.action;

    if (action === "delete") {
        records = records.filter((r) => r.id !== id);
        saveRecords();
        render();
    } else if (action === "edit") {
        const record = records.find((r) => r.id === id);
        if (!record) return;
        editIdInput.value = record.id;
        editTitleInput.value = record.title;
        editUrlInput.value = record.url;
        editTagsInput.value = record.tags.join(", ");
        bootstrap.Modal.getOrCreateInstance(document.getElementById("editModal")).show();
    }
});

editForm.addEventListener("submit", (e) => {
    e.preventDefault();
    const id = editIdInput.value;
    const record = records.find((r) => r.id === id);
    if (!record) return;
    record.title = editTitleInput.value.trim();
    record.url = editUrlInput.value.trim();
    record.tags = editTagsInput.value.split(",").map((t) => t.trim()).filter(Boolean);
    saveRecords();
    render();
    bootstrap.Modal.getInstance(document.getElementById("editModal")).hide();
});

render();
