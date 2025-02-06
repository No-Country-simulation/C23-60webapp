import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import PackageModal from "../components/packages/PackageModal";
import DesktopCarousel from "../components/common/DesktopCarousel";
import MobileCarousel from "../components/common/MobileCarousel";
import { Footer } from "../components/common/Footer";

const predefinedPackages = [
  // Aquí van tus viajes predefinidos (el array packages original)

  {
    id: 1,
    name: "Aventura en Bali",
    image: "https://images.unsplash.com/photo-1537996194471-e657df975ab4",
    price: 3299,
    duration: "10 días",
    description:
      "Explora templos antiguos, playas paradisíacas y la cultura balinesa",
    includes: ["Vuelos", "Hotel 4★", "Excursiones", "Desayunos"],
    rating: 4.8,
  },
  {
    id: 2,
    name: "Romance en París",
    image: "https://images.unsplash.com/photo-1502602898657-3e91760cbb34",
    price: 4899,
    duration: "7 días",
    description: "La ciudad del amor te espera con experiencias únicas",
    includes: ["Vuelos", "Hotel 5★", "Tours privados", "Cena en Torre Eiffel"],
    rating: 4.9,
  },
  {
    id: 3,
    name: "Japón Imperial",
    image: "https://images.unsplash.com/photo-1503899036084-c55cdd92da26",
    price: 4490,
    duration: "12 días",
    description: "Descubre la perfecta mezcla entre tradición y modernidad",
    includes: ["Vuelos", "Hotel 4★", "JR Pass", "Guía privado"],
    rating: 4.7,
  },
  {
    id: 4,
    name: "Caribe Todo Incluido",
    image:
      "https://media-cdn.tripadvisor.com/media/photo-s/2e/5f/e9/6d/cabanas-aerial.jpg",
    price: 1900,
    duration: "7 días",
    description: "Relájate en las mejores playas del Caribe",
    includes: ["Vuelos", "Resort 5★", "Todo incluido", "Actividades acuáticas"],
    rating: 4.8,
  },
  {
    id: 5,
    name: "Miami Beach, Estados Unidos.",
    image: "https://i.redd.it/o8bg0y0m9mj71.jpg",
    price: 4500,
    duration: "9 días",
    description:
      "Disfruta de las playas paradisíacas, el vibrante arte urbano y la animada vida nocturna de Miami.",
    includes: [
      "Vuelos",
      "Hotel 4★",
      "Recorrido por el arte callejero de Wynwood",
      "Paseo en bote por la Bahía de Biscayne",
      "Guía experto",
    ],
    rating: 4.9,
  },
  {
    id: 6,
    name: "Aventura en Machu Picchu",
    image:
      "https://www.civitatis.com/f/peru/urubamba/excursion-machu-picchu-grid.jpg",
    price: 2700,
    duration: "8 días",
    description:
      "Explora la misteriosa ciudadela Inca y la belleza de los Andes",
    includes: [
      "Vuelos",
      "Hotel 3★",
      "Entradas a Machu Picchu",
      "Excursiones guiadas",
    ],
    rating: 4.8,
  },
  {
    id: 7,
    name: "Rumbo a Nueva Zelanda",
    image:
      "https://media.istockphoto.com/id/450463123/video/auckland-city-new-zealand.jpg?b=1&s=640x640&k=20&c=uYXU7tqQQsWk0MjU_Y9RA7csiT3WHnJAePAYBfq0iuU=",
    price: 3699,
    duration: "11 días",
    description:
      "Descubre paisajes impresionantes entre montañas y lagos cristalinos",
    includes: ["Vuelos", "Hotel 4★", "Excursiones", "Alquiler de coche"],
    rating: 4.7,
  },
  {
    id: 8,
    name: "Cataratas del Iguazú",
    image:
      "https://cdn.getyourguide.com/img/tour/ec6989d6c563162d739d506a731ac139c517ffc0464d32d5aca82d82eac22878.jpg/97.jpg",
    price: 1897,
    duration: "5 días",
    description:
      "Maravíllate con una de las siete maravillas naturales del mundo",
    includes: [
      "Vuelos",
      "Hotel 3★",
      "Excursiones a las cataratas",
      "Traslados",
    ],
    rating: 4.9,
  },
  {
    id: 9,
    name: "Bariloche Aventura",
    image:
      "https://4.bp.blogspot.com/-P5_RT8UqKc0/WGGE6CE7t7I/AAAAAAAAIUw/Rr7pNv2V3-kX8d34b4Ijh8ivEqzdA_mewCLcB/s1600/ATAP-%2BBariloche-%2BRafting%2Bweb.jpg",
    price: 2000,
    duration: "6 días",
    description:
      "Disfruta de la naturaleza y los paisajes impresionantes de la Patagonia argentina",
    includes: [
      "Vuelos",
      "Hotel 4★",
      "Excursiones a la montaña",
      "Paseo en barco por el lago Nahuel Huapi",
    ],
    rating: 4.8,
  },
];

const recommendations = [
  {
    id: 1,
    name: "Escapada a las Montañas, Mendoza. Arg.",
    image:
      "https://www.turismoestacion.tur.ar/wp-content/uploads/2016/02/Mendoza.jpg",
    price: 799,
    originalPrice: "$999",
    duration: "5 días",
    description: "Descubre la magia de las montañas con esta oferta especial",
  },
  {
    id: 2,
    name: "Tour a Salta y Jujuy. Arg.",
    image:
      "https://photo620x400.mnstatic.com/02e75374064bf4f9c523e050d46886dd/cafayate.jpg",
    price: 1499,
    originalPrice: "$1899",
    duration: "4 días 3 noches",
    description:
      "Excursión a Cachi, Hornocal, Cerro de los 14 Colores y Travesía a las Nubes te espera",
  },
  {
    id: 3,
    name: "Relax en la Playa, Mar del Plata. Arg.",
    image: "https://www.clarin.com/2024/01/24/uNa-hl9C0_720x0__1.jpg",
    price: 899,
    originalPrice: "$1199",
    duration: "6 días",
    description: "Disfruta del sol y el mar con todo incluido, date un gusto",
  },
  {
    id: 4,
    name: "Tour a Ushuaia, Fin del Mundo",
    image:
      "https://media.staticontent.com/media/pictures/f848b417-6e06-4b1f-b88d-1f54e5c4a952",
    price: 1899,
    originalPrice: "$2299",
    duration: "5 días 4 noches",
    description:
      "Explora el Canal Beagle y maravillate con el hermoso paisaje de la ciduad!",
  },
  {
    id: 5,
    name: "Tour a Córdoba, Argentina",
    image:
      "https://cordobaturismo.gov.ar/wp-content/uploads/2018/07/Ciudad-de-Cordoba-1-scaled-1.jpg",
    price: 1299,
    originalPrice: "$1599",
    duration: "4 días 3 noches",
    description:
      "Recorre las sierras de Córdoba, visita Villa Carlos Paz, la ciudad de Córdoba.",
  },
];

const PackagesPage = () => {
  const navigate = useNavigate();
  const [selectedPackage, setSelectedPackage] = useState(null);
  const [carouselIndex, setCarouselIndex] = useState(0);
  const [isMobile, setIsMobile] = useState(false);
  const [packages, setPackages] = useState([]);

  // Detectar el tamaño de la pantalla
  useEffect(() => {
    const handleResize = () => {
      if (window.innerWidth <= 768) {
        setIsMobile(true);
      } else {
        setIsMobile(false);
      }
    };

    handleResize(); // Llamar al inicio para comprobar el tamaño de la pantalla
    window.addEventListener("resize", handleResize);

    return () => {
      window.removeEventListener("resize", handleResize);
    };
  }, []);

  // Cargar los paquetes desde localStorage y combinarlos con los paquetes predefinidos
  useEffect(() => {
    const storedPackages = JSON.parse(localStorage.getItem("products")) || [];
    setPackages([...predefinedPackages, ...storedPackages]); // Combinamos los paquetes predefinidos con los guardados
  }, []);

  const handlePackageSelect = (pkg) => {
    setSelectedPackage(pkg);
  };

  const goToNextCarousel = () => {
    if (carouselIndex < recommendations.length - 1) {
      setCarouselIndex(carouselIndex + 1);
    } else {
      setCarouselIndex(0);
    }
  };

  const goToPrevCarousel = () => {
    if (carouselIndex > 0) {
      setCarouselIndex(carouselIndex - 1);
    } else {
      setCarouselIndex(recommendations.length - 1);
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 flex flex-col">
      <div className="relative w-full h-[400px] mb-12">
        <div
          className="absolute inset-0 hero-gradient"
          style={{
            backgroundImage:
              "url('https://images.unsplash.com/photo-1469474968028-56623f02e42e')",
            backgroundSize: "cover",
            backgroundPosition: "center",
          }}
        />
        <div className="absolute inset-0 bg-primary/60 flex items-center justify-center">
          <div className="text-center text-white max-w-4xl px-4">
            <h1 className="text-4xl md:text-5xl font-bold mb-4">
              ¡Tu viaje ya está listo!
            </h1>
            <h2 className="text-2xl md:text-3xl mb-4">
              Nosotros lo armamos por vos
            </h2>
            <p className="text-lg md:text-xl">
              Elegí entre paquetes sugeridos, destinos combinados o Circuitos y
              preparate para viajar.
            </p>
          </div>
        </div>
      </div>

      <div className="container mx-auto px-4 py-8 flex-grow">
        <h2 className="text-3xl font-bold text-center mb-6">
          Recomendaciones Explora+Más en Argentina
        </h2>

        {isMobile ? (
          <MobileCarousel
            recommendations={recommendations}
            carouselIndex={carouselIndex}
            goToNextCarousel={goToNextCarousel}
            goToPrevCarousel={goToPrevCarousel}
            handlePackageSelect={handlePackageSelect}
          />
        ) : (
          <DesktopCarousel
            recommendations={recommendations}
            carouselIndex={carouselIndex}
            goToNextCarousel={goToNextCarousel}
            goToPrevCarousel={goToPrevCarousel}
            handlePackageSelect={handlePackageSelect}
          />
        )}
      </div>

      {selectedPackage && (
        <PackageModal
          selectedPackage={selectedPackage}
          onClose={() => setSelectedPackage(null)}
          onReserve={(pkg) =>
            navigate("/mis-viajes", { state: { newBooking: pkg } })
          }
        />
      )}

      <div className="mb-12 px-4 sm:px-8 lg:px-16">
        <h1 className="text-3xl sm:text-4xl font-bold text-center mb-4">
          Paquetes Turísticos Internacionales
        </h1>
        <p className="text-gray-600 text-center max-w-3xl mx-auto mb-8 text-base sm:text-lg">
          Descubre nuestros paquetes cuidadosamente diseñados para ofrecerte las
          mejores experiencias de viaje.
        </p>

        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-8">
          {packages.map((pkg) => (
            <div
              key={pkg.id}
              className=" overflow-hidden bg-white shadow-lg rounded-lg transform transition duration-300 hover:scale-105"
            >
              <img
                src={pkg.image}
                alt={pkg.name}
                className="w-full h-48 sm:h-60 object-cover rounded-t-lg"
              />
              <div className="p-6">
                <h3 className="text-xl sm:text-2xl font-bold mb-3">
                  {pkg.name}
                </h3>
                <p className=" text-gray-600 mb-4 text-sm sm:text-base">
                  {pkg.description}
                </p>
                <div className="flex justify-between items-center text-gray-600 mb-4">
                  <span className="text-sm sm:text-base">{pkg.duration}</span>
                  <span className="font-semibold text-primary text-sm sm:text-base">
                    ${pkg.price}
                  </span>
                </div>
                <div className="text-yellow-500">
                  {"★".repeat(Math.floor(pkg.rating))}
                  <span className="text-gray-400 ml-1 text-sm sm:text-base">
                    ({pkg.rating})
                  </span>
                </div>
              </div>
              <div className="p-6 pt-0">
                <button
                  className="w-full py-3 bg-[rgb(26,54,93)] text-white rounded-md hover:bg-[rgb(26,54,93,0.8)]"
                  onClick={() => handlePackageSelect(pkg)}
                >
                  Ver detalle
                </button>
              </div>
            </div>
          ))}
        </div>
      </div>

      <Footer />
    </div>
  );
};

export default PackagesPage;
