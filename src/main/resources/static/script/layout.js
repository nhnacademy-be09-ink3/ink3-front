document.addEventListener('DOMContentLoaded', () => {
    const searchButton = document.getElementById('search-button');
    const searchInput = document.getElementById('search-input');

    searchButton.addEventListener('click', () => {
        searchInput.classList.toggle('hidden');
        searchInput.classList.toggle('w-0');
        searchInput.classList.toggle('opacity-0');
        searchInput.classList.toggle('w-48');
        searchInput.classList.toggle('opacity-100');
        searchInput.classList.toggle('pl-2');
    });
});
