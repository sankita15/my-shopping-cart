const isCartEmpty = cartDetails => Object.keys(cartDetails).length !== 0 && Object.keys(cartDetails.products).length !== 0;

const getCartProducts = (cartDetails, getProduct) => Object.keys(cartDetails.products)
    .map(productId => getProduct(cartDetails.products[productId], cartDetails.productQuantities[productId]));

const getCartResponse = user => fetch(`/api/carts/user/${user}`, {
    credentials: 'include',
    headers: {
        'Content-Type': 'application/json',
    },
}).then(res => (res.ok ? res.json() : Promise.reject(res.status)))
    .then(allCarts => allCarts.filter(pendingCart => pendingCart.status === 'pending')[0])
    .then(cartDetails => ((cartDetails === undefined) ? Promise.reject(404) : Promise.resolve(cartDetails)));

const getTotalPrice = totalPrice => totalPrice;

export {
    isCartEmpty,
    getCartProducts,
    getCartResponse,
    getTotalPrice,
};
