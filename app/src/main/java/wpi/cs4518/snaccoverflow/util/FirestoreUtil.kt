package wpi.cs4518.snaccoverflow.util

import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.QuerySnapshot
import com.xwray.groupie.kotlinandroidextensions.Item
import wpi.cs4518.snaccoverflow.model.ChatChannel
import wpi.cs4518.snaccoverflow.model.Message
import wpi.cs4518.snaccoverflow.model.TextMessage
import wpi.cs4518.snaccoverflow.model.Profile
import wpi.cs4518.snaccoverflow.recyclerview.item.PersonItem
import wpi.cs4518.snaccoverflow.recyclerview.item.TextMessageItem

object FirestoreUtil {
    private val firestoreInstance: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }
    private val currentUserDocRef: DocumentReference
        get() = firestoreInstance.document(
            "users/${
                FirebaseAuth.getInstance().currentUser?.uid
                    ?: throw NullPointerException("UID is null.")
            }"
        )

    private val chatChannelsCollectionRef = firestoreInstance.collection("chatChannels")

    fun initCurrentUserIfFirstTime(onComplete: () -> Unit) {
        currentUserDocRef.get().addOnSuccessListener { documentSnapshot ->
            if (!documentSnapshot.exists()) {
                val newUser = Profile(
                    0, FirebaseAuth.getInstance().currentUser?.displayName ?: "",
                    0, "", "", ""
                )
                currentUserDocRef.set(newUser).addOnSuccessListener {
                    onComplete()
                }
            } else
                onComplete()
        }
    }

    fun updateCurrentUser(
        id: Int = 0,
        name: String = "",
        age: Int = 0,
        answerOne: String = "",
        answerTwo: String = "",
        answerThree: String = ""
    ) {
        var userFieldMap = mutableMapOf<String, Any>()
        if (id != 0) userFieldMap["id"] = id
        if (name.isNotBlank()) userFieldMap["name"] = name
        if (age != 0) userFieldMap["age"] = age
        if (answerOne.isNotBlank()) userFieldMap["answerOne"] = answerOne
        if (answerTwo.isNotBlank()) userFieldMap["answerTwo"] = answerTwo
        if (answerThree.isNotBlank()) userFieldMap["answerThree"] = answerThree

        currentUserDocRef.update(userFieldMap)
    }

    fun getCurrentUser(onComplete: (Profile) -> Unit) {
        currentUserDocRef.get()
            .addOnSuccessListener {
                it.toObject(Profile::class.java)?.let { it1 -> onComplete(it1) }
            }
    }

    fun getOrCreateChatChannel(otherUserId: String, onComplete: (channelId: String) -> Unit) {
        currentUserDocRef.collection("engagedChatChannels")
            .document(otherUserId).get().addOnSuccessListener {
                if (it.exists()) {
                    onComplete(it["channelId"] as String)
                    return@addOnSuccessListener
                }
                val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid

                val newChannel = chatChannelsCollectionRef.document()
                newChannel.set(ChatChannel(mutableListOf(currentUserId, otherUserId)))

                currentUserDocRef
                    .collection("engagedChatChannels")
                    .document(otherUserId)
                    .set(mapOf("channelId" to newChannel.id))

                firestoreInstance.collection("users").document(otherUserId)
                    .collection("engagedChatChannels")
                    .document(currentUserId)
                    .set(mapOf("channelId" to newChannel.id))
                onComplete(newChannel.id)
            }
    }

    fun addChatMessagesListener(
        channelId: String, context: Context,
        onListen: (List<Item>) -> Unit
    ): ListenerRegistration {
        return chatChannelsCollectionRef.document(channelId).collection("messages")
            .orderBy("time")
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    Log.e("FIRESTORE", "ChatMessagesListener error.", firebaseFirestoreException)
                    return@addSnapshotListener
                }
                val items = mutableListOf<Item>()
                if (querySnapshot != null) {
                    querySnapshot.documents.forEach {
                        it.toObject(TextMessage::class.java)
                            ?.let { it1 -> TextMessageItem(it1, context) }?.let { it2 ->
                                items.add(it2)
                            }
                        onListen(items)
                    }
                }

            }

    }

    fun addUsersListener(context: Context, onListen: (List<Item>) -> Unit): ListenerRegistration {
        return firestoreInstance.collection("users")
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    Log.e("FIRESTORE", "Users listener error.", firebaseFirestoreException)
                    return@addSnapshotListener
                }
                val items = mutableListOf<Item>()
                querySnapshot?.documents?.forEach {
                    if (it.id != FirebaseAuth.getInstance().currentUser?.uid)
                        it.toObject(Profile::class.java)
                            ?.let { it1 -> PersonItem(it1, it.id, context) }?.let { it2 ->
                                items.add(it2)
                            }
                }
                onListen(items)
            }
    }

    fun removeListener(userListenerRegistration: ListenerRegistration) = userListenerRegistration.remove()
    fun sendMessage(messageToSend: Any, channelId: String) {
            chatChannelsCollectionRef.document(channelId)
                .collection("messages")
                .add(messageToSend)
    }
}

