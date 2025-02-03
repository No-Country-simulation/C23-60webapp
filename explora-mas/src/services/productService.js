// src/services/productService.js
export const createPackage = async (packageData) => {
    const response = await fetch('/api/packages', {
      method: 'POST',
      body: JSON.stringify(packageData),
      headers: {
        'Content-Type': 'application/json',
      },
    });
  
    return await response.json();
  };
  