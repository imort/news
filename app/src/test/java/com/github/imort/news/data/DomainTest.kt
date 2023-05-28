package com.github.imort.news.data

import org.junit.Assert.assertEquals
import org.junit.Test

class DomainTest {
    @Test
    fun parseHeadlines() {
        val json = """
            {
                "status": "ok",
                "totalResults": 35,
                "articles": [
                    {
                        "id" : "preview",
                        "source": {
                            "id": "polygon",
                            "name": "Polygon"
                        },
                        "author": "Michael McWhertor",
                        "title": "Zelda: Tears of the Kingdom duplication glitch patched out by Nintendo - Polygon",
                        "description": "A new patch for The Legend of Zelda: Tears of Kingdom (version 1.1.2) removes the easy duplication glitches for the Nintendo Switch game.",
                        "url": "https://www.polygon.com/23738583/zelda-tears-of-the-kingdom-duplicate-items-glitch-patch",
                        "urlToImage": "https://cdn.vox-cdn.com/thumbor/sEbIZdRAR4LwupSgsyMqcc_DtPw=/0x0:1920x1005/fit-in/1200x630/cdn.vox-cdn.com/uploads/chorus_asset/file/24678066/totk_return_to_hyrule_alt.jpg",
                        "publishedAt": "2023-05-26T13:56:35Z",
                        "content": "A new patch for The Legend of Zelda: Tears of the Kingdom released Thursday addresses a number of glitches in the Switch game that let players duplicate items. Version 1.1.2 for Tears of the Kingdom,… [+1341 chars]"
                    }
                ]
            }
        """.trimIndent()

        val moshi = DataModule.moshi()
        val adapter = moshi.adapter(TopHeadlines::class.java)
        assertEquals(topHeadlines, adapter.fromJson(json))
    }
}