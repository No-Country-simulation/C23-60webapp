import React from 'react';

const DesktopCarousel = ({ recommendations, carouselIndex, goToNextCarousel, goToPrevCarousel, handlePackageSelect }) => {
  // Obtener las 3 tarjetas visibles. Si quedan menos de 3, repetimos desde el principio.
  const visibleRecommendations = [
    recommendations[carouselIndex % recommendations.length],
    recommendations[(carouselIndex + 1) % recommendations.length],
    recommendations[(carouselIndex + 2) % recommendations.length],
  ];

  return (
    <div className="relative">
      <div className="carousel-content flex gap-4 overflow-hidden">
        {/* Mostrar 3 cards a la vez en escritorio */}
        {visibleRecommendations.map((rec) => (
          <div key={rec.id} className="carousel-item w-full md:w-1/3 lg:w-1/3 p-4">
            <div className="bg-white rounded-lg shadow-lg overflow-hidden">
              <img
                src={rec.image}
                alt={rec.name}
                className="w-full h-48 object-cover"
              />
              <div className="p-4">
                <h3 className="text-xl font-bold mb-2">{rec.name}</h3>
                <p className="text-gray-600 mb-2">{rec.description}</p>
                <div className="flex justify-between items-center text-gray-600 mb-2">
                  <span>{rec.duration}</span>
                  <span className="font-semibold text-primary">${rec.price}</span>
                </div>
                <span className="text-sm text-gray-500 line-through">
                  {rec.originalPrice}
                </span>
              </div>
              <div className="p-4 pt-0">
                <button
                  className="w-full py-2 bg-[rgb(26,54,93)] text-white rounded-md hover:bg-[rgb(26,54,93,0.8)]"  
                  onClick={() => handlePackageSelect(rec)} // Aquí pasamos 'rec' a la función
                >
                  Ver oferta
                </button>
              </div>
            </div>
          </div>
        ))}
      </div>

      {/* Carousel Controls */}
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

export default DesktopCarousel;
