import React from 'react';

const MobileCarousel = ({ recommendations, carouselIndex, goToNextCarousel, goToPrevCarousel, handlePackageSelect }) => {
  return (
    <div className="relative">
      <div className="carousel-content flex gap-4 overflow-hidden">
        {/* Mostrar 1 card a la vez en móvil */}
        <div className="carousel-item w-full p-4">
          <div className="bg-white rounded-lg shadow-lg overflow-hidden">
            <img
              src={recommendations[carouselIndex].image}
              alt={recommendations[carouselIndex].name}
              className="w-full h-48 object-cover"
            />
            <div className="p-4">
              <h3 className="text-xl font-bold mb-2">{recommendations[carouselIndex].name}</h3>
              <p className="text-gray-600 mb-2">{recommendations[carouselIndex].description}</p>
              <div className="flex justify-between items-center text-gray-600 mb-2">
                <span>{recommendations[carouselIndex].duration}</span>
                <span className="font-semibold text-primary">${recommendations[carouselIndex].price}</span>
              </div>
              <span className="text-sm text-gray-500 line-through">
                {recommendations[carouselIndex].originalPrice}
              </span>
            </div>
            <div className="p-4 pt-0">
              <button
                className="w-full py-2 bg-[rgb(26,54,93)] text-white rounded-md hover:bg-[rgb(26,54,93,0.8)]"  
                onClick={() => handlePackageSelect(recommendations[carouselIndex])} // Aquí también pasamos 'rec' a la función
              >
                Ver oferta
              </button>
            </div>
          </div>
        </div>
      </div>

      {/* Navegación para cambiar de card */}
      <button
        className="absolute top-1/2 left-0 transform -translate-y-1/2 bg-gray-700 text-white p-2 rounded-full"
        onClick={goToPrevCarousel}
      >
        {"<"}
      </button>
      <button
        className="absolute top-1/2 right-0 transform -translate-y-1/2 bg-gray-700 text-white p-2 rounded-full"
        onClick={goToNextCarousel}
      >
        {">"}
      </button>
    </div>
  );
};

export default MobileCarousel;
