package com.hamza.wallpap.ui.screens.latest

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.Instant
import java.util.*
import kotlin.random.Random

data class WallpaperItem(val id: String, val url: String, val height: Int) {
    constructor() : this("", "", 0)
}

class LatestViewModel : ViewModel() {

    private val _wallpaperItems = mutableStateListOf<String>()
    private val heightMap = hashMapOf<String, Int>()
    val wallpaperItems: List<WallpaperItem>
        @RequiresApi(Build.VERSION_CODES.O)
        get() = _wallpaperItems.map { wallpaperItem ->
            val imageId = UUID.randomUUID().toString()
        WallpaperItem(id = imageId,url = wallpaperItem, height = heightFor(generateRandomID()))
    }

    private val firestore = Firebase.firestore
    private val imagesCollection = firestore.collection("images")
    private val _images = MutableLiveData<List<WallpaperItem>>()
    val images: LiveData<List<WallpaperItem>> get() = _images
    private val _shuffledImages = MutableLiveData<List<WallpaperItem>>()
    val shuffledImages: LiveData<List<WallpaperItem>> get() = _shuffledImages

    private fun fetchImages() {
        imagesCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                // Handle error
                return@addSnapshotListener
            }

            val images = snapshot?.documents?.mapNotNull { document ->
                document.toObject(WallpaperItem::class.java)
            }
            val shuffledImages = images?.shuffled()
            _images.value = images
            _shuffledImages.value = shuffledImages
//            images?.let { _shuffledImages.addAll(it) }
        }
    }

    fun shuffleImages() {
        val images = _images.value
        val shuffledImages = images?.shuffled()
        _shuffledImages.value = shuffledImages
    }


    val listOfUrls = listOf(
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792078101.jpg?alt=media&token=ad5c1558-5cd2-47fc-aa39-1d990df67585",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/amoled-yellow-full-moon-rj2yddgg7fgf4538.jpg?alt=media&token=47ce09cd-ac82-4547-912a-ecb896d1a872",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/digital-wolf-artwork-amoled-pun1e82jtj93h33c.jpg?alt=media&token=c9d37802-ee6d-4848-ba27-9b66013bfc9c",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791996511.jpg?alt=media&token=2b8cb338-5b83-49d4-8be6-46cbb9022f01",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/797845.png?alt=media&token=649c8d1f-3608-4aed-a467-1e2422e817ef",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657784749358.jpg?alt=media&token=6914ff8f-9bb2-4a48-a6fe-ab9d1be5000f",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657784665507.jpg?alt=media&token=93fb64f1-f6ca-434d-a029-4e09b71ae02b",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791812768.jpg?alt=media&token=409e9c3a-2d4e-425c-80b4-0b9291072249",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791542303.jpg?alt=media&token=ba577070-43d6-4fb4-98b3-cb9ef1464d1b",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/amoled-blue-eyed-black-cat-rq91a4klmxqxaiyb.jpg?alt=media&token=c5d33b0b-c6ea-431e-b3cf-18590890584d",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/amoled-colorful-stormtrooper-36fiz607b6rdgfzf.jpg?alt=media&token=bb4473f2-7b4e-4ac7-ad3d-d97f506c4d9f",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/cyberpunk-2077-keanu-reeves-amoled-mg9gszqfqzjgrjls.jpg?alt=media&token=4018dc79-b3f7-4131-8e4b-b2e34e866f64",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/amoled-dark-skyscrapers-4sv7gd17feafcv5b.jpg?alt=media&token=61f5af0d-b4a3-4db6-aed9-6f12d2b411e2",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/amoled-glowing-light-ball-k6d28yzm5vde51mx.jpg?alt=media&token=71db82a0-4627-4bd9-b967-08f9cc1ac28c",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/amoled-multi-colored-oreo-cookie-gu0ok8zlj6emjpz0.jpg?alt=media&token=53c7d548-c38e-4911-81b1-d76884fb895d",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/amoled-nature-silhouette-ja8bogvxxmgzmfq6.jpg?alt=media&token=6c17c41f-e104-4d8f-9c3b-2fc3bb5c46ef",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/amoled-purple-landscape-4bzkopox9euc6ugl.jpg?alt=media&token=7c295cfb-6171-4206-a4b7-0830bc1218a8",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/amoled-snowy-mountain-7d8finn73lpm7886.jpg?alt=media&token=aa36b33e-a095-48cf-8ff7-76a7ea3d3f1a",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/amoled-woman-face-2sg6cz7oq3qab0yv.jpg?alt=media&token=934b3b61-7310-42bb-a328-b8b2fea0664a",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/fantastic-amoled-mountainscape-q8p0il0581da5we6.jpg?alt=media&token=dd6505ca-949e-4ae3-9c7d-b7dffed3bdf0",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/glowing-daft-punk-amoled-loa0ebhsq04pljur.jpg?alt=media&token=54f82538-8a6d-4888-a6f9-73d3d451aef3",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657784688594.jpg?alt=media&token=da244f7e-6960-4684-a759-6a5fedab7140",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/spooky-aesthetic-grin-amoled-rah8daxdlwefxsri.jpg?alt=media&token=594840ec-d3a9-46f3-803c-98852795025d",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/surface-of-the-moon-amoled-4cu1xswhbvqa2soa.jpg?alt=media&token=d9a6b696-ffa6-495d-bc01-f34084bfb4c5",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1469493.jpg?alt=media&token=90ceb3f5-72be-4696-ad6f-79180cd83cc4",
        "https://w0.peakpx.com/wallpaper/789/24/HD-wallpaper-amoled-floral-purple-super-amoled.jpg",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792605773.jpg?alt=media&token=5ed643dd-43b3-4125-9d37-3891e7eab210",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791526526.jpg?alt=media&token=c53367a5-513b-467a-ba29-f3d7247c0467",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792592725.jpg?alt=media&token=47ef861c-f892-4fec-b9ba-50d86c25cddf",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791872365.jpg?alt=media&token=9832f46a-6bbf-4d32-b13c-3206709ef172",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791867433.jpg?alt=media&token=eb8540ca-e42a-48a9-b332-5243ed5e3d14",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791608256.jpg?alt=media&token=a75e2efb-ab1a-4cc9-8db6-c00659e51dae",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791714865.jpg?alt=media&token=4955ebda-8f6d-4d84-8412-965b77fd7781",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792622279.jpg?alt=media&token=a0ed7210-8e50-46c5-aef0-b78a0d515d5d",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792617117.jpg?alt=media&token=c3882675-eb37-4a84-92a9-730443755c6d",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791602414.jpg?alt=media&token=c34a2d17-f514-4c65-a6c9-7e322ff6a8fc",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791717769.jpg?alt=media&token=56d9eef6-8eed-41c0-9221-7861689f437e",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791535300.jpg?alt=media&token=2f3a4907-8f64-49e6-b844-4dbbc24081cc",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791927071.jpg?alt=media&token=435ee03d-e971-4689-ab05-823f7fac1635",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792614457.jpg?alt=media&token=7d58f91a-128d-4f99-b611-fe2d9b7bb5b5",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791749327.jpg?alt=media&token=8d5244f8-85f6-4e4d-b20d-925ab66c1ac2",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791633665.jpg?alt=media&token=8c0fef95-15ab-40d2-aea5-655673879a48",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792022048.jpg?alt=media&token=35bb9f5b-b668-41b0-b97c-4827b7321d02",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791658372.jpg?alt=media&token=4d155be5-6b4d-4219-a699-59fd070814af",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791458384.jpg?alt=media&token=0fef6499-f506-4a76-80d4-6615a84c67ab",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791856721.jpg?alt=media&token=5bc31232-154c-405d-8015-1bdaf4a41713",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792003029.jpg?alt=media&token=7768dd3f-dd82-4691-8915-848d00270e21",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791913025.jpg?alt=media&token=667f709b-6501-4e75-b189-af1bb2cb0496",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791865314.jpg?alt=media&token=92231843-cc4d-4a67-9f3e-e9875d85151b",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791538959.jpg?alt=media&token=63756aba-00c3-4089-be10-4c3880acbf9f",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791545546.jpg?alt=media&token=5d4877c4-e912-49bc-8795-83eb37bed790",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791580874.jpg?alt=media&token=3c27597f-4f9b-4331-81a3-8a61b2a9283e",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791590130.jpg?alt=media&token=87693b0f-6f20-48f5-8131-cbea41595546",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791597468.jpg?alt=media&token=18babea6-a5c8-4d33-bb22-406c99f0d384",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791602414.jpg?alt=media&token=c34a2d17-f514-4c65-a6c9-7e322ff6a8fc",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791604235.jpg?alt=media&token=8ee06723-de6b-44db-ae6e-8e74a5a72639",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791606149.jpg?alt=media&token=9893ba30-2618-4e06-92eb-a72173d8f21a",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791610497.jpg?alt=media&token=02316bf1-c8c0-4669-a5e1-9096b153516d",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791611974.jpg?alt=media&token=1367ca61-7653-433a-8e67-ecba7a334c89",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791624919.jpg?alt=media&token=e645e75d-9f98-4987-bfd1-44ac6daa8255",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791633665.jpg?alt=media&token=8c0fef95-15ab-40d2-aea5-655673879a48",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791654700.jpg?alt=media&token=9b411ef6-6312-4e05-ae67-ffc9c5dd2522",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791658372.jpg?alt=media&token=4d155be5-6b4d-4219-a699-59fd070814af",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791751558.jpg?alt=media&token=10170afe-f509-4296-bf8b-c47151168c9f",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791807613.jpg?alt=media&token=7210f16e-646d-4d34-b15c-718826b0bd59",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791807613.jpg?alt=media&token=7210f16e-646d-4d34-b15c-718826b0bd5933",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791856721.jpg?alt=media&token=5bc31232-154c-405d-8015-1bdaf4a41713",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791863799.jpg?alt=media&token=87aaaece-247a-4173-be4d-d5b98d799d67",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791876277.jpg?alt=media&token=7ba80974-789e-471f-864f-12ddef806050",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791917493.jpg?alt=media&token=5acaf33b-fdb5-4192-9520-0b1bb0157089",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791991149.jpg?alt=media&token=551401e5-6f3c-48dc-a2a5-b8512ead7cba",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792012977.jpg?alt=media&token=41299416-78d1-4e87-8bd9-ecc551e4a112",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792022048.jpg?alt=media&token=35bb9f5b-b668-41b0-b97c-4827b7321d02",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792078101.jpg?alt=media&token=ad5c1558-5cd2-47fc-aa39-1d990df67585",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792093703.jpg?alt=media&token=37769a2d-c63f-4d4f-bf1c-38872803fb74",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792592725.jpg?alt=media&token=47ef861c-f892-4fec-b9ba-50d86c25cddf",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/peakpx%20(9).jpg?alt=media&token=d88086ea-9f3b-4684-a05d-bb3075525a09",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/peakpx%20(8).jpg?alt=media&token=1a1a9618-8510-4984-aed2-b6a5cf070c20",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/peakpx%20(7).jpg?alt=media&token=a29fc66a-7c07-48e9-9bd9-ea8daab2f094",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/peakpx%20(6).jpg?alt=media&token=5cba6037-482c-41d0-9a56-b58507dd1dc7",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/peakpx%20(5).jpg?alt=media&token=ca047f74-eded-43ae-adee-083045f1176e",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/peakpx%20(4).jpg?alt=media&token=c20cea47-4d4d-4593-85d8-7b286c6f16c1",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/peakpx%20(3).jpg?alt=media&token=f10ab03d-71e0-48a0-8264-681294996562",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/peakpx%20(2).jpg?alt=media&token=710e165d-3c81-401e-8879-cd8d79229736",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/peakpx%20(17).jpg?alt=media&token=742fa09e-a0a8-4e6e-8af6-fa76f9dd0320",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/peakpx%20(16).jpg?alt=media&token=efb94a0a-3d79-49e2-ab41-f6579e7cdcef",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/peakpx%20(15).jpg?alt=media&token=0c9bebf1-603b-4b01-9285-af740af5e54e",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/peakpx%20(14).jpg?alt=media&token=a42681ff-0a77-4746-9d9f-7726123b40ec",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/peakpx%20(13).jpg?alt=media&token=ef44f1bc-2568-434d-b3bb-1f3c1e7c82a5",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/peakpx%20(12).jpg?alt=media&token=3cd7a0fd-7a23-4f3d-ab86-9726ba5c3716",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/peakpx%20(11).jpg?alt=media&token=1d74e32a-7fc6-449f-abba-3a4e553a77da",
        "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/peakpx%20(10).jpg?alt=media&token=9bc1c663-07c0-454d-a043-fb4a39de0134"
    )

    init {
        fetchImages()
        // Initialize the wallpaper items with the initial data
        _wallpaperItems.addAll(
            listOfUrls
//            listOf(
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792078101.jpg?alt=media&token=ad5c1558-5cd2-47fc-aa39-1d990df67585",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/amoled-yellow-full-moon-rj2yddgg7fgf4538.jpg?alt=media&token=47ce09cd-ac82-4547-912a-ecb896d1a872",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/digital-wolf-artwork-amoled-pun1e82jtj93h33c.jpg?alt=media&token=c9d37802-ee6d-4848-ba27-9b66013bfc9c",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791996511.jpg?alt=media&token=2b8cb338-5b83-49d4-8be6-46cbb9022f01",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/797845.png?alt=media&token=649c8d1f-3608-4aed-a467-1e2422e817ef",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657784749358.jpg?alt=media&token=6914ff8f-9bb2-4a48-a6fe-ab9d1be5000f",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657784665507.jpg?alt=media&token=93fb64f1-f6ca-434d-a029-4e09b71ae02b",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791812768.jpg?alt=media&token=409e9c3a-2d4e-425c-80b4-0b9291072249",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791542303.jpg?alt=media&token=ba577070-43d6-4fb4-98b3-cb9ef1464d1b",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/amoled-blue-eyed-black-cat-rq91a4klmxqxaiyb.jpg?alt=media&token=c5d33b0b-c6ea-431e-b3cf-18590890584d",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/amoled-colorful-stormtrooper-36fiz607b6rdgfzf.jpg?alt=media&token=bb4473f2-7b4e-4ac7-ad3d-d97f506c4d9f",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/cyberpunk-2077-keanu-reeves-amoled-mg9gszqfqzjgrjls.jpg?alt=media&token=4018dc79-b3f7-4131-8e4b-b2e34e866f64",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/amoled-dark-skyscrapers-4sv7gd17feafcv5b.jpg?alt=media&token=61f5af0d-b4a3-4db6-aed9-6f12d2b411e2",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/amoled-glowing-light-ball-k6d28yzm5vde51mx.jpg?alt=media&token=71db82a0-4627-4bd9-b967-08f9cc1ac28c",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/amoled-multi-colored-oreo-cookie-gu0ok8zlj6emjpz0.jpg?alt=media&token=53c7d548-c38e-4911-81b1-d76884fb895d",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/amoled-nature-silhouette-ja8bogvxxmgzmfq6.jpg?alt=media&token=6c17c41f-e104-4d8f-9c3b-2fc3bb5c46ef",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/amoled-purple-landscape-4bzkopox9euc6ugl.jpg?alt=media&token=7c295cfb-6171-4206-a4b7-0830bc1218a8",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/amoled-snowy-mountain-7d8finn73lpm7886.jpg?alt=media&token=aa36b33e-a095-48cf-8ff7-76a7ea3d3f1a",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/amoled-woman-face-2sg6cz7oq3qab0yv.jpg?alt=media&token=934b3b61-7310-42bb-a328-b8b2fea0664a",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/fantastic-amoled-mountainscape-q8p0il0581da5we6.jpg?alt=media&token=dd6505ca-949e-4ae3-9c7d-b7dffed3bdf0",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/glowing-daft-punk-amoled-loa0ebhsq04pljur.jpg?alt=media&token=54f82538-8a6d-4888-a6f9-73d3d451aef3",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657784688594.jpg?alt=media&token=da244f7e-6960-4684-a759-6a5fedab7140",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/spooky-aesthetic-grin-amoled-rah8daxdlwefxsri.jpg?alt=media&token=594840ec-d3a9-46f3-803c-98852795025d",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/surface-of-the-moon-amoled-4cu1xswhbvqa2soa.jpg?alt=media&token=d9a6b696-ffa6-495d-bc01-f34084bfb4c5",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1469493.jpg?alt=media&token=90ceb3f5-72be-4696-ad6f-79180cd83cc4",
//            "https://w0.peakpx.com/wallpaper/789/24/HD-wallpaper-amoled-floral-purple-super-amoled.jpg",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792605773.jpg?alt=media&token=5ed643dd-43b3-4125-9d37-3891e7eab210",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791526526.jpg?alt=media&token=c53367a5-513b-467a-ba29-f3d7247c0467",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792592725.jpg?alt=media&token=47ef861c-f892-4fec-b9ba-50d86c25cddf",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791872365.jpg?alt=media&token=9832f46a-6bbf-4d32-b13c-3206709ef172",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791867433.jpg?alt=media&token=eb8540ca-e42a-48a9-b332-5243ed5e3d14",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791608256.jpg?alt=media&token=a75e2efb-ab1a-4cc9-8db6-c00659e51dae",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791714865.jpg?alt=media&token=4955ebda-8f6d-4d84-8412-965b77fd7781",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792622279.jpg?alt=media&token=a0ed7210-8e50-46c5-aef0-b78a0d515d5d",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792617117.jpg?alt=media&token=c3882675-eb37-4a84-92a9-730443755c6d",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791602414.jpg?alt=media&token=c34a2d17-f514-4c65-a6c9-7e322ff6a8fc",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791717769.jpg?alt=media&token=56d9eef6-8eed-41c0-9221-7861689f437e",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791535300.jpg?alt=media&token=2f3a4907-8f64-49e6-b844-4dbbc24081cc",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791927071.jpg?alt=media&token=435ee03d-e971-4689-ab05-823f7fac1635",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792614457.jpg?alt=media&token=7d58f91a-128d-4f99-b611-fe2d9b7bb5b5",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791749327.jpg?alt=media&token=8d5244f8-85f6-4e4d-b20d-925ab66c1ac2",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791633665.jpg?alt=media&token=8c0fef95-15ab-40d2-aea5-655673879a48",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792022048.jpg?alt=media&token=35bb9f5b-b668-41b0-b97c-4827b7321d02",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791658372.jpg?alt=media&token=4d155be5-6b4d-4219-a699-59fd070814af",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791458384.jpg?alt=media&token=0fef6499-f506-4a76-80d4-6615a84c67ab",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791856721.jpg?alt=media&token=5bc31232-154c-405d-8015-1bdaf4a41713",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792003029.jpg?alt=media&token=7768dd3f-dd82-4691-8915-848d00270e21",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791913025.jpg?alt=media&token=667f709b-6501-4e75-b189-af1bb2cb0496",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791865314.jpg?alt=media&token=92231843-cc4d-4a67-9f3e-e9875d85151b",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791538959.jpg?alt=media&token=63756aba-00c3-4089-be10-4c3880acbf9f",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791545546.jpg?alt=media&token=5d4877c4-e912-49bc-8795-83eb37bed790",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791580874.jpg?alt=media&token=3c27597f-4f9b-4331-81a3-8a61b2a9283e",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791590130.jpg?alt=media&token=87693b0f-6f20-48f5-8131-cbea41595546",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791597468.jpg?alt=media&token=18babea6-a5c8-4d33-bb22-406c99f0d384",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791602414.jpg?alt=media&token=c34a2d17-f514-4c65-a6c9-7e322ff6a8fc",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791604235.jpg?alt=media&token=8ee06723-de6b-44db-ae6e-8e74a5a72639",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791606149.jpg?alt=media&token=9893ba30-2618-4e06-92eb-a72173d8f21a",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791610497.jpg?alt=media&token=02316bf1-c8c0-4669-a5e1-9096b153516d",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791611974.jpg?alt=media&token=1367ca61-7653-433a-8e67-ecba7a334c89",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791624919.jpg?alt=media&token=e645e75d-9f98-4987-bfd1-44ac6daa8255",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791633665.jpg?alt=media&token=8c0fef95-15ab-40d2-aea5-655673879a48",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791654700.jpg?alt=media&token=9b411ef6-6312-4e05-ae67-ffc9c5dd2522",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791658372.jpg?alt=media&token=4d155be5-6b4d-4219-a699-59fd070814af",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791751558.jpg?alt=media&token=10170afe-f509-4296-bf8b-c47151168c9f",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791807613.jpg?alt=media&token=7210f16e-646d-4d34-b15c-718826b0bd59",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791807613.jpg?alt=media&token=7210f16e-646d-4d34-b15c-718826b0bd5933",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791856721.jpg?alt=media&token=5bc31232-154c-405d-8015-1bdaf4a41713",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791863799.jpg?alt=media&token=87aaaece-247a-4173-be4d-d5b98d799d67",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791876277.jpg?alt=media&token=7ba80974-789e-471f-864f-12ddef806050",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791917493.jpg?alt=media&token=5acaf33b-fdb5-4192-9520-0b1bb0157089",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791991149.jpg?alt=media&token=551401e5-6f3c-48dc-a2a5-b8512ead7cba",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792012977.jpg?alt=media&token=41299416-78d1-4e87-8bd9-ecc551e4a112",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792022048.jpg?alt=media&token=35bb9f5b-b668-41b0-b97c-4827b7321d02",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792078101.jpg?alt=media&token=ad5c1558-5cd2-47fc-aa39-1d990df67585",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792093703.jpg?alt=media&token=37769a2d-c63f-4d4f-bf1c-38872803fb74",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792592725.jpg?alt=media&token=47ef861c-f892-4fec-b9ba-50d86c25cddf",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/peakpx%20(9).jpg?alt=media&token=d88086ea-9f3b-4684-a05d-bb3075525a09",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/peakpx%20(8).jpg?alt=media&token=1a1a9618-8510-4984-aed2-b6a5cf070c20",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/peakpx%20(7).jpg?alt=media&token=a29fc66a-7c07-48e9-9bd9-ea8daab2f094",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/peakpx%20(6).jpg?alt=media&token=5cba6037-482c-41d0-9a56-b58507dd1dc7",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/peakpx%20(5).jpg?alt=media&token=ca047f74-eded-43ae-adee-083045f1176e",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/peakpx%20(4).jpg?alt=media&token=c20cea47-4d4d-4593-85d8-7b286c6f16c1",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/peakpx%20(3).jpg?alt=media&token=f10ab03d-71e0-48a0-8264-681294996562",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/peakpx%20(2).jpg?alt=media&token=710e165d-3c81-401e-8879-cd8d79229736",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/peakpx%20(17).jpg?alt=media&token=742fa09e-a0a8-4e6e-8af6-fa76f9dd0320",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/peakpx%20(16).jpg?alt=media&token=efb94a0a-3d79-49e2-ab41-f6579e7cdcef",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/peakpx%20(15).jpg?alt=media&token=0c9bebf1-603b-4b01-9285-af740af5e54e",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/peakpx%20(14).jpg?alt=media&token=a42681ff-0a77-4746-9d9f-7726123b40ec",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/peakpx%20(13).jpg?alt=media&token=ef44f1bc-2568-434d-b3bb-1f3c1e7c82a5",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/peakpx%20(12).jpg?alt=media&token=3cd7a0fd-7a23-4f3d-ab86-9726ba5c3716",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/peakpx%20(11).jpg?alt=media&token=1d74e32a-7fc6-449f-abba-3a4e553a77da",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/peakpx%20(10).jpg?alt=media&token=9bc1c663-07c0-454d-a043-fb4a39de0134"
//        )
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addImagesToFirestore(imageUrls: List<String>) {
        val firestore = Firebase.firestore
        val batch = firestore.batch()

        val collectionRef = firestore.collection("images")
        for (imageUrl in imageUrls) {
//            val imageId = UUID.randomUUID().toString()
            val imageId = generateRandomID()
            val image = WallpaperItem(imageId, imageUrl, heightFor(imageId))
            val documentRef = collectionRef.document(imageId)
            batch.set(documentRef, image)
        }

        batch.commit()
            .addOnSuccessListener {
                // Batch write successful
            }
            .addOnFailureListener { exception ->
                // Handle error
            }
    }




    fun refreshWallpaperItems() {
        val updatedData = listOf(
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792078101.jpg?alt=media&token=ad5c1558-5cd2-47fc-aa39-1d990df67585",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/amoled-yellow-full-moon-rj2yddgg7fgf4538.jpg?alt=media&token=47ce09cd-ac82-4547-912a-ecb896d1a872",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/digital-wolf-artwork-amoled-pun1e82jtj93h33c.jpg?alt=media&token=c9d37802-ee6d-4848-ba27-9b66013bfc9c",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791996511.jpg?alt=media&token=2b8cb338-5b83-49d4-8be6-46cbb9022f01",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/797845.png?alt=media&token=649c8d1f-3608-4aed-a467-1e2422e817ef",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657784749358.jpg?alt=media&token=6914ff8f-9bb2-4a48-a6fe-ab9d1be5000f",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657784665507.jpg?alt=media&token=93fb64f1-f6ca-434d-a029-4e09b71ae02b",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791812768.jpg?alt=media&token=409e9c3a-2d4e-425c-80b4-0b9291072249",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791542303.jpg?alt=media&token=ba577070-43d6-4fb4-98b3-cb9ef1464d1b",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/amoled-blue-eyed-black-cat-rq91a4klmxqxaiyb.jpg?alt=media&token=c5d33b0b-c6ea-431e-b3cf-18590890584d",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/amoled-colorful-stormtrooper-36fiz607b6rdgfzf.jpg?alt=media&token=bb4473f2-7b4e-4ac7-ad3d-d97f506c4d9f",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/cyberpunk-2077-keanu-reeves-amoled-mg9gszqfqzjgrjls.jpg?alt=media&token=4018dc79-b3f7-4131-8e4b-b2e34e866f64",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/amoled-dark-skyscrapers-4sv7gd17feafcv5b.jpg?alt=media&token=61f5af0d-b4a3-4db6-aed9-6f12d2b411e2",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/amoled-glowing-light-ball-k6d28yzm5vde51mx.jpg?alt=media&token=71db82a0-4627-4bd9-b967-08f9cc1ac28c",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/amoled-multi-colored-oreo-cookie-gu0ok8zlj6emjpz0.jpg?alt=media&token=53c7d548-c38e-4911-81b1-d76884fb895d",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/amoled-nature-silhouette-ja8bogvxxmgzmfq6.jpg?alt=media&token=6c17c41f-e104-4d8f-9c3b-2fc3bb5c46ef",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/amoled-purple-landscape-4bzkopox9euc6ugl.jpg?alt=media&token=7c295cfb-6171-4206-a4b7-0830bc1218a8",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/amoled-snowy-mountain-7d8finn73lpm7886.jpg?alt=media&token=aa36b33e-a095-48cf-8ff7-76a7ea3d3f1a",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/amoled-woman-face-2sg6cz7oq3qab0yv.jpg?alt=media&token=934b3b61-7310-42bb-a328-b8b2fea0664a",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/fantastic-amoled-mountainscape-q8p0il0581da5we6.jpg?alt=media&token=dd6505ca-949e-4ae3-9c7d-b7dffed3bdf0",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/glowing-daft-punk-amoled-loa0ebhsq04pljur.jpg?alt=media&token=54f82538-8a6d-4888-a6f9-73d3d451aef3",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657784688594.jpg?alt=media&token=da244f7e-6960-4684-a759-6a5fedab7140",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/spooky-aesthetic-grin-amoled-rah8daxdlwefxsri.jpg?alt=media&token=594840ec-d3a9-46f3-803c-98852795025d",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/surface-of-the-moon-amoled-4cu1xswhbvqa2soa.jpg?alt=media&token=d9a6b696-ffa6-495d-bc01-f34084bfb4c5",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1469493.jpg?alt=media&token=90ceb3f5-72be-4696-ad6f-79180cd83cc4",
            "https://w0.peakpx.com/wallpaper/789/24/HD-wallpaper-amoled-floral-purple-super-amoled.jpg",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792605773.jpg?alt=media&token=5ed643dd-43b3-4125-9d37-3891e7eab210",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791526526.jpg?alt=media&token=c53367a5-513b-467a-ba29-f3d7247c0467",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792592725.jpg?alt=media&token=47ef861c-f892-4fec-b9ba-50d86c25cddf",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791872365.jpg?alt=media&token=9832f46a-6bbf-4d32-b13c-3206709ef172",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791867433.jpg?alt=media&token=eb8540ca-e42a-48a9-b332-5243ed5e3d14",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791608256.jpg?alt=media&token=a75e2efb-ab1a-4cc9-8db6-c00659e51dae",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791714865.jpg?alt=media&token=4955ebda-8f6d-4d84-8412-965b77fd7781",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792622279.jpg?alt=media&token=a0ed7210-8e50-46c5-aef0-b78a0d515d5d",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792617117.jpg?alt=media&token=c3882675-eb37-4a84-92a9-730443755c6d",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791602414.jpg?alt=media&token=c34a2d17-f514-4c65-a6c9-7e322ff6a8fc",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791717769.jpg?alt=media&token=56d9eef6-8eed-41c0-9221-7861689f437e",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791535300.jpg?alt=media&token=2f3a4907-8f64-49e6-b844-4dbbc24081cc",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791927071.jpg?alt=media&token=435ee03d-e971-4689-ab05-823f7fac1635",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792614457.jpg?alt=media&token=7d58f91a-128d-4f99-b611-fe2d9b7bb5b5",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791749327.jpg?alt=media&token=8d5244f8-85f6-4e4d-b20d-925ab66c1ac2",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791633665.jpg?alt=media&token=8c0fef95-15ab-40d2-aea5-655673879a48",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792022048.jpg?alt=media&token=35bb9f5b-b668-41b0-b97c-4827b7321d02",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791658372.jpg?alt=media&token=4d155be5-6b4d-4219-a699-59fd070814af",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791458384.jpg?alt=media&token=0fef6499-f506-4a76-80d4-6615a84c67ab",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791856721.jpg?alt=media&token=5bc31232-154c-405d-8015-1bdaf4a41713",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792003029.jpg?alt=media&token=7768dd3f-dd82-4691-8915-848d00270e21",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791913025.jpg?alt=media&token=667f709b-6501-4e75-b189-af1bb2cb0496",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791865314.jpg?alt=media&token=92231843-cc4d-4a67-9f3e-e9875d85151b",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791538959.jpg?alt=media&token=63756aba-00c3-4089-be10-4c3880acbf9f",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791545546.jpg?alt=media&token=5d4877c4-e912-49bc-8795-83eb37bed790",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791580874.jpg?alt=media&token=3c27597f-4f9b-4331-81a3-8a61b2a9283e",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791590130.jpg?alt=media&token=87693b0f-6f20-48f5-8131-cbea41595546",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791597468.jpg?alt=media&token=18babea6-a5c8-4d33-bb22-406c99f0d384",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791602414.jpg?alt=media&token=c34a2d17-f514-4c65-a6c9-7e322ff6a8fc",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791604235.jpg?alt=media&token=8ee06723-de6b-44db-ae6e-8e74a5a72639",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791606149.jpg?alt=media&token=9893ba30-2618-4e06-92eb-a72173d8f21a",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791610497.jpg?alt=media&token=02316bf1-c8c0-4669-a5e1-9096b153516d",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791611974.jpg?alt=media&token=1367ca61-7653-433a-8e67-ecba7a334c89",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791624919.jpg?alt=media&token=e645e75d-9f98-4987-bfd1-44ac6daa8255",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791633665.jpg?alt=media&token=8c0fef95-15ab-40d2-aea5-655673879a48",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791654700.jpg?alt=media&token=9b411ef6-6312-4e05-ae67-ffc9c5dd2522",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791658372.jpg?alt=media&token=4d155be5-6b4d-4219-a699-59fd070814af",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791751558.jpg?alt=media&token=10170afe-f509-4296-bf8b-c47151168c9f",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791807613.jpg?alt=media&token=7210f16e-646d-4d34-b15c-718826b0bd59",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791807613.jpg?alt=media&token=7210f16e-646d-4d34-b15c-718826b0bd5933",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791856721.jpg?alt=media&token=5bc31232-154c-405d-8015-1bdaf4a41713",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791863799.jpg?alt=media&token=87aaaece-247a-4173-be4d-d5b98d799d67",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791876277.jpg?alt=media&token=7ba80974-789e-471f-864f-12ddef806050",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791917493.jpg?alt=media&token=5acaf33b-fdb5-4192-9520-0b1bb0157089",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791991149.jpg?alt=media&token=551401e5-6f3c-48dc-a2a5-b8512ead7cba",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792012977.jpg?alt=media&token=41299416-78d1-4e87-8bd9-ecc551e4a112",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792022048.jpg?alt=media&token=35bb9f5b-b668-41b0-b97c-4827b7321d02",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792078101.jpg?alt=media&token=ad5c1558-5cd2-47fc-aa39-1d990df67585",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792093703.jpg?alt=media&token=37769a2d-c63f-4d4f-bf1c-38872803fb74",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792592725.jpg?alt=media&token=47ef861c-f892-4fec-b9ba-50d86c25cddf",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/peakpx%20(9).jpg?alt=media&token=d88086ea-9f3b-4684-a05d-bb3075525a09",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/peakpx%20(8).jpg?alt=media&token=1a1a9618-8510-4984-aed2-b6a5cf070c20",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/peakpx%20(7).jpg?alt=media&token=a29fc66a-7c07-48e9-9bd9-ea8daab2f094",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/peakpx%20(6).jpg?alt=media&token=5cba6037-482c-41d0-9a56-b58507dd1dc7",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/peakpx%20(5).jpg?alt=media&token=ca047f74-eded-43ae-adee-083045f1176e",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/peakpx%20(4).jpg?alt=media&token=c20cea47-4d4d-4593-85d8-7b286c6f16c1",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/peakpx%20(3).jpg?alt=media&token=f10ab03d-71e0-48a0-8264-681294996562",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/peakpx%20(2).jpg?alt=media&token=710e165d-3c81-401e-8879-cd8d79229736",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/peakpx%20(17).jpg?alt=media&token=742fa09e-a0a8-4e6e-8af6-fa76f9dd0320",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/peakpx%20(16).jpg?alt=media&token=efb94a0a-3d79-49e2-ab41-f6579e7cdcef",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/peakpx%20(15).jpg?alt=media&token=0c9bebf1-603b-4b01-9285-af740af5e54e",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/peakpx%20(14).jpg?alt=media&token=a42681ff-0a77-4746-9d9f-7726123b40ec",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/peakpx%20(13).jpg?alt=media&token=ef44f1bc-2568-434d-b3bb-1f3c1e7c82a5",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/peakpx%20(12).jpg?alt=media&token=3cd7a0fd-7a23-4f3d-ab86-9726ba5c3716",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/peakpx%20(11).jpg?alt=media&token=1d74e32a-7fc6-449f-abba-3a4e553a77da",
            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/peakpx%20(10).jpg?alt=media&token=9bc1c663-07c0-454d-a043-fb4a39de0134"
            //356.12kb
        )

        _wallpaperItems.clear()
        _wallpaperItems.addAll(updatedData)
    }

//    val wallpaperItems =
//        listOf(
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792078101.jpg?alt=media&token=ad5c1558-5cd2-47fc-aa39-1d990df67585",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/amoled-yellow-full-moon-rj2yddgg7fgf4538.jpg?alt=media&token=47ce09cd-ac82-4547-912a-ecb896d1a872",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/digital-wolf-artwork-amoled-pun1e82jtj93h33c.jpg?alt=media&token=c9d37802-ee6d-4848-ba27-9b66013bfc9c",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791996511.jpg?alt=media&token=2b8cb338-5b83-49d4-8be6-46cbb9022f01",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/797845.png?alt=media&token=649c8d1f-3608-4aed-a467-1e2422e817ef",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657784749358.jpg?alt=media&token=6914ff8f-9bb2-4a48-a6fe-ab9d1be5000f",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657784665507.jpg?alt=media&token=93fb64f1-f6ca-434d-a029-4e09b71ae02b",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791812768.jpg?alt=media&token=409e9c3a-2d4e-425c-80b4-0b9291072249",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791542303.jpg?alt=media&token=ba577070-43d6-4fb4-98b3-cb9ef1464d1b",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/amoled-blue-eyed-black-cat-rq91a4klmxqxaiyb.jpg?alt=media&token=c5d33b0b-c6ea-431e-b3cf-18590890584d",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/amoled-colorful-stormtrooper-36fiz607b6rdgfzf.jpg?alt=media&token=bb4473f2-7b4e-4ac7-ad3d-d97f506c4d9f",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/cyberpunk-2077-keanu-reeves-amoled-mg9gszqfqzjgrjls.jpg?alt=media&token=4018dc79-b3f7-4131-8e4b-b2e34e866f64",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/amoled-dark-skyscrapers-4sv7gd17feafcv5b.jpg?alt=media&token=61f5af0d-b4a3-4db6-aed9-6f12d2b411e2",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/amoled-glowing-light-ball-k6d28yzm5vde51mx.jpg?alt=media&token=71db82a0-4627-4bd9-b967-08f9cc1ac28c",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/amoled-multi-colored-oreo-cookie-gu0ok8zlj6emjpz0.jpg?alt=media&token=53c7d548-c38e-4911-81b1-d76884fb895d",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/amoled-nature-silhouette-ja8bogvxxmgzmfq6.jpg?alt=media&token=6c17c41f-e104-4d8f-9c3b-2fc3bb5c46ef",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/amoled-purple-landscape-4bzkopox9euc6ugl.jpg?alt=media&token=7c295cfb-6171-4206-a4b7-0830bc1218a8",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/amoled-snowy-mountain-7d8finn73lpm7886.jpg?alt=media&token=aa36b33e-a095-48cf-8ff7-76a7ea3d3f1a",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/amoled-woman-face-2sg6cz7oq3qab0yv.jpg?alt=media&token=934b3b61-7310-42bb-a328-b8b2fea0664a",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/fantastic-amoled-mountainscape-q8p0il0581da5we6.jpg?alt=media&token=dd6505ca-949e-4ae3-9c7d-b7dffed3bdf0",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/glowing-daft-punk-amoled-loa0ebhsq04pljur.jpg?alt=media&token=54f82538-8a6d-4888-a6f9-73d3d451aef3",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657784688594.jpg?alt=media&token=da244f7e-6960-4684-a759-6a5fedab7140",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/spooky-aesthetic-grin-amoled-rah8daxdlwefxsri.jpg?alt=media&token=594840ec-d3a9-46f3-803c-98852795025d",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/surface-of-the-moon-amoled-4cu1xswhbvqa2soa.jpg?alt=media&token=d9a6b696-ffa6-495d-bc01-f34084bfb4c5",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1469493.jpg?alt=media&token=90ceb3f5-72be-4696-ad6f-79180cd83cc4",
//            "https://w0.peakpx.com/wallpaper/789/24/HD-wallpaper-amoled-floral-purple-super-amoled.jpg",
//
//
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792605773.jpg?alt=media&token=5ed643dd-43b3-4125-9d37-3891e7eab210",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791526526.jpg?alt=media&token=c53367a5-513b-467a-ba29-f3d7247c0467",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792592725.jpg?alt=media&token=47ef861c-f892-4fec-b9ba-50d86c25cddf",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791872365.jpg?alt=media&token=9832f46a-6bbf-4d32-b13c-3206709ef172",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791867433.jpg?alt=media&token=eb8540ca-e42a-48a9-b332-5243ed5e3d14",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791608256.jpg?alt=media&token=a75e2efb-ab1a-4cc9-8db6-c00659e51dae",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791714865.jpg?alt=media&token=4955ebda-8f6d-4d84-8412-965b77fd7781",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792622279.jpg?alt=media&token=a0ed7210-8e50-46c5-aef0-b78a0d515d5d",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792617117.jpg?alt=media&token=c3882675-eb37-4a84-92a9-730443755c6d",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791602414.jpg?alt=media&token=c34a2d17-f514-4c65-a6c9-7e322ff6a8fc",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791717769.jpg?alt=media&token=56d9eef6-8eed-41c0-9221-7861689f437e",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791535300.jpg?alt=media&token=2f3a4907-8f64-49e6-b844-4dbbc24081cc",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791927071.jpg?alt=media&token=435ee03d-e971-4689-ab05-823f7fac1635",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792614457.jpg?alt=media&token=7d58f91a-128d-4f99-b611-fe2d9b7bb5b5",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791749327.jpg?alt=media&token=8d5244f8-85f6-4e4d-b20d-925ab66c1ac2",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791633665.jpg?alt=media&token=8c0fef95-15ab-40d2-aea5-655673879a48",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792022048.jpg?alt=media&token=35bb9f5b-b668-41b0-b97c-4827b7321d02",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791658372.jpg?alt=media&token=4d155be5-6b4d-4219-a699-59fd070814af",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791458384.jpg?alt=media&token=0fef6499-f506-4a76-80d4-6615a84c67ab",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791856721.jpg?alt=media&token=5bc31232-154c-405d-8015-1bdaf4a41713",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792003029.jpg?alt=media&token=7768dd3f-dd82-4691-8915-848d00270e21",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791913025.jpg?alt=media&token=667f709b-6501-4e75-b189-af1bb2cb0496",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791865314.jpg?alt=media&token=92231843-cc4d-4a67-9f3e-e9875d85151b",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791538959.jpg?alt=media&token=63756aba-00c3-4089-be10-4c3880acbf9f",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791545546.jpg?alt=media&token=5d4877c4-e912-49bc-8795-83eb37bed790",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791580874.jpg?alt=media&token=3c27597f-4f9b-4331-81a3-8a61b2a9283e",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791590130.jpg?alt=media&token=87693b0f-6f20-48f5-8131-cbea41595546",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791597468.jpg?alt=media&token=18babea6-a5c8-4d33-bb22-406c99f0d384",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791602414.jpg?alt=media&token=c34a2d17-f514-4c65-a6c9-7e322ff6a8fc",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791604235.jpg?alt=media&token=8ee06723-de6b-44db-ae6e-8e74a5a72639",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791606149.jpg?alt=media&token=9893ba30-2618-4e06-92eb-a72173d8f21a",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791610497.jpg?alt=media&token=02316bf1-c8c0-4669-a5e1-9096b153516d",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791611974.jpg?alt=media&token=1367ca61-7653-433a-8e67-ecba7a334c89",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791624919.jpg?alt=media&token=e645e75d-9f98-4987-bfd1-44ac6daa8255",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791633665.jpg?alt=media&token=8c0fef95-15ab-40d2-aea5-655673879a48",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791654700.jpg?alt=media&token=9b411ef6-6312-4e05-ae67-ffc9c5dd2522",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791658372.jpg?alt=media&token=4d155be5-6b4d-4219-a699-59fd070814af",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791751558.jpg?alt=media&token=10170afe-f509-4296-bf8b-c47151168c9f",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791807613.jpg?alt=media&token=7210f16e-646d-4d34-b15c-718826b0bd59",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791807613.jpg?alt=media&token=7210f16e-646d-4d34-b15c-718826b0bd5933",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791856721.jpg?alt=media&token=5bc31232-154c-405d-8015-1bdaf4a41713",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791863799.jpg?alt=media&token=87aaaece-247a-4173-be4d-d5b98d799d67",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791876277.jpg?alt=media&token=7ba80974-789e-471f-864f-12ddef806050",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791917493.jpg?alt=media&token=5acaf33b-fdb5-4192-9520-0b1bb0157089",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657791991149.jpg?alt=media&token=551401e5-6f3c-48dc-a2a5-b8512ead7cba",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792012977.jpg?alt=media&token=41299416-78d1-4e87-8bd9-ecc551e4a112",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792022048.jpg?alt=media&token=35bb9f5b-b668-41b0-b97c-4827b7321d02",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792078101.jpg?alt=media&token=ad5c1558-5cd2-47fc-aa39-1d990df67585",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792093703.jpg?alt=media&token=37769a2d-c63f-4d4f-bf1c-38872803fb74",
//            "https://firebasestorage.googleapis.com/v0/b/wallpap-4f199.appspot.com/o/1657792592725.jpg?alt=media&token=47ef861c-f892-4fec-b9ba-50d86c25cddf",
//            //356.12kb
//        )

    private fun heightFor(imageId: String) : Int {
        return heightMap[imageId] ?: Random.nextInt(140, 380).also {
            heightMap[imageId] = it
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun generateRandomID(): String {
        val currentTime = Instant.now().toEpochMilli()
        val randomSuffix = Random.nextInt(1000)
        return "$currentTime$randomSuffix"
    }

    var saturationSliderValue = mutableStateOf(1f)
    var saturationSliderPosition = mutableStateOf(1f)
}