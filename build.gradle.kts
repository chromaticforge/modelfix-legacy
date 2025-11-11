plugins {
    id("java")
    id("xyz.wagyourtail.unimined") version ("1.4.1")
}

group = "net.mehvahdjukaar"
version = "1.10"

unimined.useGlobalCache = false

val main = sourceSets.main.get()
val fabric by sourceSets.creating
val ornithe by sourceSets.creating

unimined.minecraft {
    version("1.8.9")
    side("client")

    mappings {
        calamus()
        feather(28)
    }

    defaultRemapJar = false
}

unimined.minecraft(fabric) {
    combineWith(main)

    legacyFabric {
        loader("0.17.3")
    }

    defaultRemapJar = true
}

unimined.minecraft(ornithe) {
    combineWith(fabric)

    ornitheFabric {
        loader("0.17.3")
    }

    defaultRemapJar = true
}