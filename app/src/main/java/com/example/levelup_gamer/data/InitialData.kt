package com.example.levelup_gamer.data

import com.example.levelup_gamer.data.models.Product

val initialProducts = listOf(
    Product(
        id = "JM001",
        category = "Juegos de Mesa",
        name = "Catan",
        price = "29.990",
        logoImageUrl = "catan_logo",
        description = "Catan no es un juego, es una experiencia. Sumérgete en la estrategia y la negociación mientras compites por ser la fuerza dominante en la isla. Cada partida es una nueva aventura gracias a su tablero modular. Ideal para 3-4 jugadores.",
        discountedPrice = "23.990",
        imageUrls = listOf("catan_1", "catan_2", "catan_3"),
        stock = 100
    ),
    Product(
        id = "JM002",
        category = "Juegos de Mesa",
        name = "Carcassonne",
        price = "24.990",
        logoImageUrl = "carcassonne_logo",
        description = "Construye un mundo medieval ficha a ficha. Carcassonne es un juego elegante y accesible donde cada turno expande el mapa y tus posibilidades. Perfecto para iniciar a nuevos jugadores en el hobby.",
        imageUrls = listOf("carcassonne_1", "carcassonne_2"),
        stock = 100
    ),
    Product(
        id = "AC001",
        category = "Accesorios",
        name = "Controlador Inalámbrico Xbox Series X",
        price = "59.990",
        logoImageUrl = "xbox_controller_logo",
        description = "Siente cada disparo y cada textura con el nuevo diseño ergonómico y los gatillos de impulso. Un control que redefine la precisión y la inmersión. Compatible con consolas Xbox y PC.",
        imageUrls = listOf("xbox_controller_1", "xbox_controller_2", "xbox_controller_3", "xbox_controller_4", "xbox_controller_5"),
        stock = 100
    ),
    Product(
        id = "AC002",
        category = "Accesorios",
        name = "Auriculares Gamer HyperX Cloud II",
        price = "79.990",
        logoImageUrl = "hyperx_cloud_2_logo",
        description = "Escucha a tus enemigos antes de que te vean. El sonido envolvente 7.1 y la comodidad legendaria de los Cloud II te darán la ventaja competitiva que necesitas para ganar.",
        discountedPrice = "63.990",
        imageUrls = listOf("hyperx_1","hyperx_2", "hyperx_3", "hyperx_4"),
        stock = 100
    ),
    Product(
        id = "CO001",
        category = "Consolas",
        name = "PlayStation 5",
        price = "549.990",
        logoImageUrl = "ps5_logo",
        description = "La nueva generación de videojuegos ya está aquí. Con tiempos de carga casi instantáneos y gráficos que te dejarán sin aliento, la PS5 es una bestia de la inmersión.",
        imageUrls = listOf("ps5_1", "ps5_2", "ps5_3"),
        stock = 100
    ),
    Product(
        id = "CG001",
        category = "Computadores Gamers",
        name = "PC Gamer ASUS ROG Strix",
        price = "1.299.990",
        logoImageUrl = "pc_gamer_asus_logo",
        description = "Potencia bruta para los gamers más exigentes. Con componentes de última generación y un diseño que grita 'gaming', este PC está listo para cualquier desafío que le lances.",
        imageUrls = listOf("pc_gamer_asus_1", "pc_gamer_asus_2"),
        stock = 100
    ),
    Product(
        id = "SG001",
        category = "Sillas Gamers",
        name = "Silla Gamer Secretlab Titan",
        price = "349.990",
        logoImageUrl = "silla_gamer_secretlab_logo",
        description = "El trono que todo gamer merece. Diseñada para un confort y soporte inigualables durante horas, la Secretlab Titan es la inversión definitiva para tu espalda y tu rendimiento.",
        imageUrls = listOf("silla_gamer_sl_1", "silla_gamer_sl_2"),
        stock = 100
    ),
    Product(
        id = "MS001",
        category = "Mouse",
        name = "Mouse Gamer Logitech G502 HERO",
        price = "49.990",
        logoImageUrl = "mouse_logitech_g502_logo",
        description = "El mouse más vendido del mundo, ahora con el sensor HERO. Precisión, personalización y un diseño que se adapta a tu mano. ¿Qué más se puede pedir?",
        discountedPrice = "39.990",
        imageUrls = listOf("logitech_g502_1", "logitech_g502_2"),
        stock = 100
    ),
    Product(
        id = "MP001",
        category = "Mousepad",
        name = "Mousepad Razer Goliathus Extended Chroma",
        price = "29.990",
        logoImageUrl = "mousepad_razer_logo",
        description = "No es solo un mousepad, es una declaración. Con una superficie optimizada para todos los sensores y la iluminación RGB de Razer Chroma, tu escritorio nunca volverá a ser el mismo.",
        imageUrls = listOf("mousepad_razer_1", "mousepad_razer_2"),
        stock = 100
    ),
    Product(
        id = "PP001",
        category = "Poleras Personalizadas",
        name = "Polera Gamer Personalizada 'Level-Up'",
        price = "14.990",
        logoImageUrl = "polera_levelup_logo",
        description = "Viste tu pasión. Confeccionada con algodón de alta calidad y la opción de personalizarla con tu gamertag, esta polera es la forma perfecta de llevar tu identidad gamer a todas partes.",
        imageUrls = listOf("polera_levelup_1", "polera_levelup_2"),
        stock = 100
    )
)
