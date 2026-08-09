package com.example.data.repository

import com.example.data.Song
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class SongsRepository {

    val allSongs: List<Song> = listOf(
        Song(
            id = "ddlj_tujhe_dekha",
            title = "Tujhe Dekha Toh Yeh Jaana Sanam",
            movie = "Dilwale Dulhania Le Jayenge",
            singers = "Kumar Sanu, Lata Mangeshkar",
            year = 1995,
            streamUrl = "https://ia800302.us.archive.org/28/items/TujheDekhaToYehJanaSanam_201608/Tujhe%20Dekha%20To%20Yeh%20Jana%20Sanam.mp3",
            coverArtUrl = "https://images.unsplash.com/photo-1518609878373-06d740f60d8b?auto=format&fit=crop&w=600&q=80",
            durationSec = 302,
            category = "Romance 90s",
            lyrics = """
                Tujhe dekha toh yeh jaana sanam
                Pyaar hota hai deewana sanam
                Tujhe dekha toh yeh jaana sanam
                Pyaar hota hai deewana sanam
                
                Ab yahan se kahan jaayein hum
                Teri baahon mein mar jaayein hum
                Tujhe dekha toh yeh jaana sanam...
                
                Aankhen meri, sapne tere
                Dil mera, yaadein teri
                O, mera hai kya, sab kuch tera
                Jaan teri, saansein teri
                Anjaani raahon mein, tu jo mila
                Mera har rasta, tera rasta...
            """.trimIndent(),
            trivia = "DDLJ completed over 1000 consecutive weeks at the Maratha Mandir theatre in Mumbai! Kumar Sanu's soulful vocal became the romantic anthem of the entire 1990s generation."
        ),
        Song(
            id = "dil_se_chaiyya",
            title = "Chaiyya Chaiyya",
            movie = "Dil Se..",
            singers = "Sukhwinder Singh, Sapna Awasthi",
            year = 1998,
            streamUrl = "https://ia800308.us.archive.org/30/items/ChaiyyaChaiyya/Chaiyya%20Chaiyya.mp3",
            coverArtUrl = "https://images.unsplash.com/photo-1511671782779-c97d3d27a1d4?auto=format&fit=crop&w=600&q=80",
            durationSec = 398,
            category = "Front Seat Party",
            lyrics = """
                Jinke sar ho ishq ki chhaon
                Pao ke neeche jannat hogi
                Jinke sar ho ishq ki chhaon
                
                Chal chaiyya chaiyya chaiyya chaiyya
                Gulposh kabhi dekho patti patti
                Yaaram hi seam hai yarra hi yarra
                
                Woh yaar hai jo khushboo ki tarah
                Woh jiski zuban urdu ki tarah
                Meri shaam raat meri kaainat
                Woh yaar mera saiyaan saiyaan...
            """.trimIndent(),
            trivia = "A.R. Rahman composed this iconic masterpiece, which was filmed on top of a moving steam train (Nilgiri Mountain Railway) with Shah Rukh Khan and Malaika Arora without safety harnesses!"
        ),
        Song(
            id = "jjws_pehla_nasha",
            title = "Pehla Nasha",
            movie = "Jo Jeeta Wohi Sikandar",
            singers = "Udit Narayan, Sadhana Sargam",
            year = 1992,
            streamUrl = "https://ia801004.us.archive.org/21/items/PehlaNasha_201704/Pehla%20Nasha.mp3",
            coverArtUrl = "https://images.unsplash.com/photo-1470225620780-dba8ba36b745?auto=format&fit=crop&w=600&q=80",
            durationSec = 290,
            category = "Night Bus Romance",
            lyrics = """
                Pehla nasha, pehla khumar
                Naya pyaar hai, naya intezar
                Kar loon main kya apna haal
                Aaye ho dil mein pehli baar
                
                Pehla nasha, pehla khumar...
                
                Udata hi phiroon in hawaon mein kahin
                Ya main jhool jaaoon in ghataon mein kahin
                Ek kar doon aasmaan aur zameen
                Kaho yaaro kya karoon kya nahi...
            """.trimIndent(),
            trivia = "Choreographed by Farah Khan, this was Bollywood's first iconic full slow-motion song sequence!"
        ),
        Song(
            id = "mohra_tip_tip",
            title = "Tip Tip Barsa Paani",
            movie = "Mohra",
            singers = "Udit Narayan, Alka Yagnik",
            year = 1994,
            streamUrl = "https://ia800707.us.archive.org/18/items/TipTipBarsaPaani/Tip%20Tip%20Barsa%20Paani.mp3",
            coverArtUrl = "https://images.unsplash.com/photo-1515694346937-94d85e41e6f0?auto=format&fit=crop&w=600&q=80",
            durationSec = 358,
            category = "Rain Bus Special",
            lyrics = """
                Tip tip barsa paani
                Paani ne aag lagai
                Aag lagi dil mein toh
                Dil ko teri yaad aai
                Teri yaad aai toh
                Jal gaya mera bheega badan
                Ab tum hi batao saajan
                Main kya karoon...
            """.trimIndent(),
            trivia = "Raveena Tandon performed this sizzling rain dance with a high fever on a construction site set!"
        ),
        Song(
            id = "criminal_tum_mile",
            title = "Tum Mile Dil Khile",
            movie = "Criminal",
            singers = "Kumar Sanu, K.S. Chithra",
            year = 1995,
            streamUrl = "https://ia800109.us.archive.org/16/items/TumMileDilKhile/Tum%20Mile%20Dil%20Khile.mp3",
            coverArtUrl = "https://images.unsplash.com/photo-1514525253161-7a46d19cd819?auto=format&fit=crop&w=600&q=80",
            durationSec = 370,
            category = "Night Bus Romance",
            lyrics = """
                Tum mile dil khile
                Aur jeene ko kya chahiye
                Tum mile dil khile
                Aur jeene ko kya chahiye
                
                Na ho tu udaas, tere paas paas
                Main rahoon sada
                Na ho tu udaas, tere paas paas
                Main rahoon sada
                Baahon mein bharlon, zulfon se khelon
                Tum mile dil khile...
            """.trimIndent(),
            trivia = "Composed by M.M. Keeravani (composer of RRR!), this song created waves across South and North India alike in 1995."
        ),
        Song(
            id = "hddcs_aankhon_ki",
            title = "Aankhon Ki Gustakhiyan",
            movie = "Hum Dil De Chuke Sanam",
            singers = "Kumar Sanu, Kavita Krishnamurthy",
            year = 1999,
            streamUrl = "https://ia800305.us.archive.org/12/items/AankhonKiGustakhiyan/Aankhon%20Ki%20Gustakhiyan.mp3",
            coverArtUrl = "https://images.unsplash.com/photo-1492684223066-81342ee5ff30?auto=format&fit=crop&w=600&q=80",
            durationSec = 300,
            category = "Chai Break Duets",
            lyrics = """
                Aankhon ki gustakhiyan maaf ho
                O aankhon ki gustakhiyan maaf ho
                Ek tuk tumhe dekhti hain
                Jo baat kehna chaahe zuban
                Ye usse kehti hain
                
                Aankhon ki shararatein maaf hon
                Aankhon ki shararatein maaf hon
                
                Aapke dil ko churaati hain
                Aapke dil mein chhupke se
                Ye dhadkan badhaati hain...
            """.trimIndent(),
            trivia = "Ismail Darbar received the National Film Award for Best Music Direction for Hum Dil De Chuke Sanam."
        ),
        Song(
            id = "main_khiladi_chura_ke",
            title = "Chura Ke Dil Mera",
            movie = "Main Khiladi Tu Anari",
            singers = "Kumar Sanu, Alka Yagnik",
            year = 1994,
            streamUrl = "https://ia800501.us.archive.org/15/items/ChuraKeDilMera_201608/Chura%20Ke%20Dil%20Mera.mp3",
            coverArtUrl = "https://images.unsplash.com/photo-1465847899084-d164df4dedc6?auto=format&fit=crop&w=600&q=80",
            durationSec = 285,
            category = "Front Seat Party",
            lyrics = """
                Chura ke dil mera goriya chali
                Uda ke nindiyan kahan tu chali
                Paas aane laga zara zara
                Mera dil deewana hone laga
                
                Aag chahat ki lagne lagi
                Mera rasta tu takne lagi
                Chura ke dil mera goriya chali...
            """.trimIndent(),
            trivia = "Anu Malik composed this track which launched Shilpa Shetty's breakthrough dance sensation in 1994!"
        ),
        Song(
            id = "raja_hindustani_aaye_ho",
            title = "Aaye Ho Meri Zindagi Mein",
            movie = "Raja Hindustani",
            singers = "Udit Narayan, Alka Yagnik",
            year = 1996,
            streamUrl = "https://ia800702.us.archive.org/24/items/AayeHoMeriZindagiMein/Aaye%20Ho%20Meri%20Zindagi%20Mein.mp3",
            coverArtUrl = "https://images.unsplash.com/photo-1518837695005-2083093ee35b?auto=format&fit=crop&w=600&q=80",
            durationSec = 362,
            category = "Night Bus Romance",
            lyrics = """
                Aaye ho meri zindagi mein tum bahaar ban ke
                Mere dil mein yunhi rehna tum pyaar pyaar ban ke
                Ankhon mein tum base ho sapna hazar ban ke
                Mere dil mein yunhi rehna tum pyaar pyaar ban ke
                
                Ghoonghat mein har kali thi rangon mein na dhali thi
                Aaye ho meri zindagi mein tum bahaar ban ke...
            """.trimIndent(),
            trivia = "Nadeem-Shravan composed Raja Hindustani's album which sold over 1 crore (10 million) cassette tapes in India!"
        ),
        Song(
            id = "kkhh_title",
            title = "Kuch Kuch Hota Hai",
            movie = "Kuch Kuch Hota Hai",
            singers = "Udit Narayan, Alka Yagnik",
            year = 1998,
            streamUrl = "https://ia800300.us.archive.org/31/items/KuchKuchHotaHai_201608/Kuch%20Kuch%20Hota%20Hai.mp3",
            coverArtUrl = "https://images.unsplash.com/photo-1528722828814-77b9b83aafb2?auto=format&fit=crop&w=600&q=80",
            durationSec = 296,
            category = "Chai Break Duets",
            lyrics = """
                Tum paas aaye, yun muskuraye
                Tumne na jaane kya sapne dikhaye
                Ab toh mera dil, jaage na sota hai
                Kya karoon haye, kuch kuch hota hai
                
                Na jaane kaisa ehsaas hai
                Bujhti nahin hai jo pyaas hai
                Kya nasha is pyaar ka
                Mujhpe sanam chhane laga...
            """.trimIndent(),
            trivia = "Jatin-Lalit's iconic piano theme intro became the universal romantic melody of college canteens and bus trips in the late 90s!"
        ),
        Song(
            id = "taal_se_taal",
            title = "Taal Se Taal Mila",
            movie = "Taal",
            singers = "Alka Yagnik, Udit Narayan",
            year = 1999,
            streamUrl = "https://ia800204.us.archive.org/27/items/TaalSeTaalMila/Taal%20Se%20Taal%20Mila.mp3",
            coverArtUrl = "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=600&q=80",
            durationSec = 320,
            category = "Rain Bus Special",
            lyrics = """
                Dil ye bechain ve, raste pe nain ve
                Jindari behaal hai, sur hai na taal hai
                Aaja saawan punam aaja
                
                Taal se taal mila, taal se taal mila
                
                Sawan ne aag lagai, dil ko teri yaad aai
                Mera bheega bheega badan, bole tu aaja saajan
                Aaja re aaja re...
            """.trimIndent(),
            trivia = "A.R. Rahman's soundtrack for Taal became the first Hindi film album to reach the top 20 in the US Billboard charts!"
        ),
        Song(
            id = "saajan_bahut_pyar",
            title = "Bahut Pyar Karte Hain",
            movie = "Saajan",
            singers = "Anuradha Paudwal, S.P. Balasubrahmanyam",
            year = 1991,
            streamUrl = "https://ia800803.us.archive.org/32/items/BahutPyarKarteHain/Bahut%20Pyar%20Karte%20Hain.mp3",
            coverArtUrl = "https://images.unsplash.com/photo-1509114397022-ed747cca3f65?auto=format&fit=crop&w=600&q=80",
            durationSec = 280,
            category = "Dhaba Stop Melodies",
            lyrics = """
                Bahut pyar karte hain tumko sanam
                Bahut pyar karte hain tumko sanam
                Kasam chahe le lo, khuda ki kasam
                
                Hamari wafa pe na shak karna tum
                Tumhein chahte hain jaan se bhi zyada
                Kasam chahe le lo, khuda ki kasam...
            """.trimIndent(),
            trivia = "Saajan was the highest-grossing Bollywood soundtrack of 1991 with unforgettable tracks by Kumar Sanu, SPB, and Anuradha Paudwal."
        ),
        Song(
            id = "aashiqui_dheere_dheere",
            title = "Dheere Dheere Se Meri Zindagi",
            movie = "Aashiqui",
            singers = "Kumar Sanu, Anuradha Paudwal",
            year = 1990,
            streamUrl = "https://ia800100.us.archive.org/29/items/DheereDheereSeMeriZindagi/Dheere%20Dheere%20Se%20Meri%20Zindagi.mp3",
            coverArtUrl = "https://images.unsplash.com/photo-1518609878373-06d740f60d8b?auto=format&fit=crop&w=600&q=80",
            durationSec = 315,
            category = "Night Bus Romance",
            lyrics = """
                Dheere dheere se meri zindagi mein aana
                Dheere dheere se mere dil ko churaana
                Tumse pyaar mujhe hai kitna jaaneman
                Tumse milkar tumko hai bataana
                
                Jab se tujhe dekha dil ko kahin aaraam nahi
                Mere honton pe ek tere siva koi naam nahi...
            """.trimIndent(),
            trivia = "Aashiqui's album sold over 20 million cassette tapes in India, making it the highest-selling Hindi film album of all time!"
        ),
        Song(
            id = "aashiqui_nazar_ke_samne",
            title = "Nazar Ke Samne Jigar Ke Paas",
            movie = "Aashiqui",
            singers = "Kumar Sanu, Anuradha Paudwal",
            year = 1990,
            streamUrl = "https://ia800201.us.archive.org/19/items/NazarKeSamne/Nazar%20Ke%20Samne.mp3",
            coverArtUrl = "https://images.unsplash.com/photo-1470225620780-dba8ba36b745?auto=format&fit=crop&w=600&q=80",
            durationSec = 330,
            category = "Night Bus Romance",
            lyrics = """
                Nazar ke samne jigar ke paas
                Koi rehta hai wo ho tum
                Nazar ke samne jigar ke paas
                Koi rehta hai wo ho tum
                
                Betaabiyon ke wo sabhi haseen pal
                Yaadon mein rehte hain har ek kal...
            """.trimIndent(),
            trivia = "Nazar Ke Samne won Filmfare awards for Best Male Playback Singer (Kumar Sanu) and Best Lyricist (Sameer)."
        ),
        Song(
            id = "pktdk_o_o_jaane_jaana",
            title = "O O Jaane Jaana",
            movie = "Pyaar Kiya To Darna Kya",
            singers = "Kamaal Khan",
            year = 1998,
            streamUrl = "https://ia800303.us.archive.org/11/items/OOJaaneJaana/O%20O%20Jaane%20Jaana.mp3",
            coverArtUrl = "https://images.unsplash.com/photo-1511671782779-c97d3d27a1d4?auto=format&fit=crop&w=600&q=80",
            durationSec = 345,
            category = "Front Seat Party",
            lyrics = """
                O o jaane jaana, dhundhe tujhe deewana
                Sapno mein roz aaye, aa zindagi mein aana sanam
                
                Mera dil na kisi pe aaya
                Bada naam hai iss dil ka
                Har koi chahat mein hai
                Pehli nazar ka ye jaadoo...
            """.trimIndent(),
            trivia = "Salman Khan's shirtless guitar dance in this Jatin-Lalit track became an instant pop culture icon!"
        ),
        Song(
            id = "dtph_title",
            title = "Dil To Pagal Hai",
            movie = "Dil To Pagal Hai",
            singers = "Lata Mangeshkar, Udit Narayan",
            year = 1997,
            streamUrl = "https://ia800502.us.archive.org/22/items/DilToPagalHai_201608/Dil%20To%20Pagal%20Hai.mp3",
            coverArtUrl = "https://images.unsplash.com/photo-1514525253161-7a46d19cd819?auto=format&fit=crop&w=600&q=80",
            durationSec = 338,
            category = "Chai Break Duets",
            lyrics = """
                Dil to pagal hai, dil deewana hai
                Pehli pehli baar milaata hai yeh
                Phir kya hota hai jungle mein
                Aag lagaata hai yeh
                
                Dil to pagal hai, dil deewana hai...
            """.trimIndent(),
            trivia = "Uttam Singh composed the music for Dil To Pagal Hai, which swept 3 National Film Awards and 8 Filmfare Awards!"
        )
    )

    fun getSongsByCategory(category: String): List<Song> {
        if (category == "Sabhi Gaane" || category == "ALL") return allSongs
        return allSongs.filter { it.category.contains(category, ignoreCase = true) || it.title.contains(category, ignoreCase = true) }
    }

    fun searchSongs(query: String): List<Song> {
        if (query.isBlank()) return allSongs
        val q = query.trim().lowercase()
        return allSongs.filter {
            it.title.lowercase().contains(q) ||
            it.singers.lowercase().contains(q) ||
            it.movie.lowercase().contains(q) ||
            it.year.toString().contains(q)
        }
    }

    fun getSongById(id: String): Song? {
        return allSongs.find { it.id == id }
    }
}
