import { useState, useEffect } from "react";
import { FaEdit, FaTrashAlt } from "react-icons/fa";
import { useForm, useFieldArray } from "react-hook-form";

const AdminPage = () => {
  const API_URL = "https://exploramas.onrender.com";
  const {
    register,
    handleSubmit,
    control,
    formState: { errors },
    reset,
  } = useForm();
  const { fields, append, remove } = useFieldArray({
    control,
    name: "discounts",
  });
  const [imagePreview, setImagePreview] = useState(null);
  const [products, setProducts] = useState([]);

  const token = localStorage.getItem("token")?.trim() || "";
  /*
  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const response = await fetch(`${API_URL}/admin/users`, {
          method: "GET",
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        if (response.status === 201) {
          console.log("Get hecho con éxito");
        }

        if (!response.ok) {
          throw new Error("Error al cargar los productos");
        }
        const data = await response.json();
        setProducts(data);
      } catch (error) {
        console.error(error);
      }
    };

    fetchProducts();
  }, []);

  */
  const handleImageChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onloadend = () => {
        setImagePreview(reader.result);
      };
      reader.readAsDataURL(file);
    }
  };

  const onSubmit = async (data) => {
    const formData = new FormData();

    // Crear un objeto con los datos del DTO
    const dto = {
      title: data.name,
      description: data.description,
      destiny: data.destiny,
      startDate: data.startDate,
      endDate: data.endDate,
      availableBundles: data.avaidableBundles,
      unitaryPrice: data.price,
    };

    // Convertir DTO a JSON y agregarlo a FormData
    formData.append(
      "travelBundleRequestDTO",
      new Blob([JSON.stringify(dto)], { type: "application/json" }),
    );

    // Agregar imagen como lista de archivos
    if (data.image && data.image.length > 0) {
      formData.append("images", data.image[0]);
    }

    try {
      const response = await fetch(
        `${API_URL}/admin/travel-bundles/create-travel-bundle`,
        {
          method: "POST",
          body: formData,
          headers: {
            Authorization: `Bearer ${token}`,
          },
        },
      );

      if (response.status === 201) {
        console.log("Post hecho con éxito");
      }

      if (response.status === 403) {
        alert("Error con el servidor");
      }

      if (!response.ok) {
        throw new Error("Error al agregar el producto");
      }

      reset();
      setImagePreview(null);
    } catch (error) {
      console.error(error);
    }
  };

  const handleDeleteProduct = async (productId) => {
    try {
      const response = await fetch(
        `${API_URL}/admin/travel-bundles/${productId}`,
        {
          method: "DELETE",
        },
      );

      if (response.status === 201) {
        console.log("Delete hecho con éxito");
      }

      if (!response.ok) {
        throw new Error("Error al eliminar el producto");
      }

      const updatedProducts = products.filter(
        (product) => product.id !== productId,
      );
      setProducts(updatedProducts);
    } catch (error) {
      console.error(error);
    }
  };

  const handleEditProduct = (productId) => {
    const productToEdit = products.find((product) => product.id === productId);
    if (productToEdit) {
      reset({
        name: productToEdit.name,
        description: productToEdit.description,
        destiny: productToEdit.destiny,
        startDate: productToEdit.startDate,
        endDate: productToEdit.endDate,
        avaidableBundles: productToEdit.avaidableBundles,
        price: productToEdit.price,
        discounts: productToEdit.discounts,
        image: productToEdit.image,
      });

      setImagePreview(productToEdit.image);
      handleDeleteProduct(productId);
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 flex flex-col">
      <div className="bg-white p-6 rounded-lg shadow-lg mb-8">
        <h3 className="text-2xl font-semibold text-gray-800 mb-4">
          Nombre de paquete
        </h3>
        <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
          {/* Nombre del producto */}
          <div>
            <label className="block text-sm font-medium text-gray-700">
              Nombre
            </label>
            <input
              type="text"
              {...register("name", {
                required: "Este campo es obligatorio",
              })}
              className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-primary focus:border-primary"
            />
            {errors.name && (
              <p className="text-red-500 text-sm mt-1">{errors.name.message}</p>
            )}
          </div>

          {/* Descripción del producto */}
          <div>
            <label className="block text-sm font-medium text-gray-700">
              Descripción
            </label>
            <textarea
              {...register("description", {
                required: "Este campo es obligatorio",
              })}
              className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-primary focus:border-primary"
            />
            {errors.description && (
              <p className="text-red-500 text-sm mt-1">
                {errors.description.message}
              </p>
            )}
          </div>

          {/* Destino */}
          <div>
            <label className="block text-sm font-medium text-gray-700">
              Destino
            </label>
            <input
              type="text"
              {...register("destiny", {
                required: "Este campo es obligatorio",
              })}
              className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-primary focus:border-primary"
            />
            {errors.destiny && (
              <p className="text-red-500 text-sm mt-1">
                {errors.destiny.message}
              </p>
            )}
          </div>

          {/* Fecha de inicio */}
          <div>
            <label className="block text-sm font-medium text-gray-700">
              Fecha de inicio
            </label>
            <input
              type="date"
              {...register("startDate", {
                required: "Este campo es obligatorio",
              })}
              className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-primary focus:border-primary"
            />
            {errors.startDate && (
              <p className="text-red-500 text-sm mt-1">
                {errors.startDate.message}
              </p>
            )}
          </div>

          {/* Fecha de fin */}
          <div>
            <label className="block text-sm font-medium text-gray-700">
              Fecha de fin
            </label>
            <input
              type="date"
              {...register("endDate", {
                required: "Este campo es obligatorio",
              })}
              className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-primary focus:border-primary"
            />
            {errors.endDate && (
              <p className="text-red-500 text-sm mt-1">
                {errors.endDate.message}
              </p>
            )}
          </div>

          {/* Paquetes disponibles */}
          <div>
            <label className="block text-sm font-medium text-gray-700">
              Paquetes disponibles
            </label>
            <input
              type="number"
              {...register("avaidableBundles", {
                required: "Este campo es obligatorio",
              })}
              className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-primary focus:border-primary"
            />
            {errors.avaidableBundles && (
              <p className="text-red-500 text-sm mt-1">
                {errors.avaidableBundles.message}
              </p>
            )}
          </div>

          {/* Precio */}
          <div>
            <label className="block text-sm font-medium text-gray-700">
              Precio
            </label>
            <input
              type="number"
              {...register("price", {
                required: "Este campo es obligatorio",
              })}
              className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-primary focus:border-primary"
            />
            {errors.price && (
              <p className="text-red-500 text-sm mt-1">
                {errors.price.message}
              </p>
            )}
          </div>

          {/* Descuentos */}
          <div>
            <label className="block text-sm font-medium text-gray-700">
              Descuentos
            </label>
            {fields.map((field, index) => (
              <div key={field.id} className="flex items-center space-x-2">
                <input
                  type="text"
                  {...register(`discounts.${index}.name`)}
                  className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-primary focus:border-primary"
                  placeholder="Nombre del descuento"
                />
                <input
                  type="number"
                  {...register(`discounts.${index}.value`)}
                  className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-primary focus:border-primary"
                  placeholder="Valor del descuento"
                />
                <button
                  type="button"
                  onClick={() => remove(index)}
                  className="p-2 bg-red-500 text-white rounded-full hover:bg-red-600 transition-colors"
                >
                  <FaTrashAlt size={16} />
                </button>
              </div>
            ))}
            <button
              type="button"
              onClick={() => append({ name: "", value: 0 })}
              className="mt-2 p-2 bg-blue-500 text-white rounded-full hover:bg-blue-600 transition-colors"
            >
              Añadir Descuento
            </button>
          </div>

          {/* Imagen */}
          <div>
            <label className="block text-sm font-medium text-gray-700">
              Imagen
            </label>
            <input
              type="file"
              {...register("image", {
                required: "Este campo es obligatorio",
              })}
              onChange={handleImageChange}
              className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-primary focus:border-primary"
            />
            {errors.image && (
              <p className="text-red-500 text-sm mt-1">
                {errors.image.message}
              </p>
            )}
            {imagePreview && (
              <div className="mt-4">
                <img
                  src={imagePreview}
                  alt="Vista previa de la imagen"
                  className="w-32 h-32 object-cover rounded-lg"
                />
              </div>
            )}
          </div>

          <button
            type="submit"
            className="w-full py-3 bg-gradient-to-r from-[#FF7C5D] to-[#FF5A38] text-white rounded-lg"
          >
            Agregar Producto
          </button>
        </form>
      </div>

      <div className="bg-white p-6 rounded-lg shadow-lg">
        <h3 className="text-2xl font-semibold text-gray-800 mb-4">
          Productos Agregados
        </h3>
        <div className="space-y-4">
          {products.length > 0 ? (
            products.map((product) => (
              <div
                key={product.id}
                className="flex items-center space-x-6 bg-white shadow-lg rounded-lg p-4"
              >
                {/* Imagen y nombre del producto */}
                <div className="flex-shrink-0">
                  <img
                    src={product.image}
                    alt={product.name}
                    className="w-32 h-32 object-cover rounded-lg"
                  />
                </div>
                <div className="flex-grow">
                  <h3 className="text-xl font-bold text-gray-800 mb-2">
                    {product.name}
                  </h3>
                  <p className="text-sm text-gray-600 mb-2">
                    {product.description}
                  </p>
                  <div className="flex gap-3 text-gray-600">
                    <p>Destino: {product.destiny}</p>
                    <p>Fecha de inicio: {product.startDate}</p>
                    <p>Fecha de fin: {product.endDate}</p>
                    <p>Paquetes disponibles: {product.avaidableBundles}</p>
                    <span className="text-sm">{product.duration}</span>
                    <span className="font-semibold text-primary text-sm">
                      <p>Precio: ${product.price}</p>
                      <p>Descuentos:</p>
                      {product.discounts.map((discount, index) => (
                        <p key={index}>
                          {discount.name}: ${discount.value}
                        </p>
                      ))}
                    </span>
                  </div>
                </div>

                {/* Botones de editar y eliminar */}
                <div className="flex space-x-4">
                  <button
                    onClick={() => handleEditProduct(product.id)}
                    className="p-2 bg-yellow-500 text-white rounded-full hover:bg-yellow-600 transition-colors"
                  >
                    <FaEdit size={20} />
                  </button>
                  <button
                    onClick={() => handleDeleteProduct(product.id)}
                    className="p-2 bg-red-500 text-white rounded-full hover:bg-red-600 transition-colors"
                  >
                    <FaTrashAlt size={20} />
                  </button>
                </div>
              </div>
            ))
          ) : (
            <p className="text-gray-600">No hay productos agregados aún.</p>
          )}
        </div>
      </div>
    </div>
  );
};

export default AdminPage;
