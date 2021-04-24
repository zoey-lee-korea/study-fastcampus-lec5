(async () => {
    const url = 'http://localhost:8080/restaurant';
    const response = await fetch(url);
    const restaurants = await response.json();
    console.log(restaurants);

    const element = document.getElementById('app');
    //element.innerHTML = JSON.stringify(restaurants);
    element.innerHTML = `
        ${restaurants.map(restaurant => `
            <p>
                ${restaurant.id}
                ${restaurant.name}
                ${restaurant.address}
            </p>
        `).join('')}
    `;

})();