package dev.meboard.engine.api

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.locks.ReadWriteLock
import java.util.concurrent.locks.ReentrantReadWriteLock

/**
 * A simple in-memory [Dictionary] backed by a prefix trie.
 *
 * Thread-safe: reads use a read lock, writes use a write lock.
 * All operations are O(L) where L is the length of the token text.
 */
class TrieDictionary : Dictionary {

    private data class TrieNode(
        val children: MutableMap<Char, TrieNode> = ConcurrentHashMap(),
        var token: Token? = null
    )

    private val root = TrieNode()
    private val sizeTracker = java.util.concurrent.atomic.AtomicInteger(0)
    private val lock: ReadWriteLock = ReentrantReadWriteLock()

    override fun prefixMatches(prefix: String, limit: Int): List<Token> {
        val normalized = normalize(prefix)
        lock.readLock().lock()
        return try {
            val node = navigate(normalized) ?: return emptyList()
            val results = mutableListOf<Token>()
            collect(node, results, limit)
            results
        } finally {
            lock.readLock().unlock()
        }
    }

    override fun get(text: String): Token? {
        val normalized = normalize(text)
        lock.readLock().lock()
        return try {
            navigate(normalized)?.token
        } finally {
            lock.readLock().unlock()
        }
    }

    override fun put(token: Token) {
        val normalized = normalize(token.text)
        if (normalized.isEmpty()) return
        lock.writeLock().lock()
        try {
            var node = root
            for (ch in normalized) {
                node = node.children.getOrPut(ch) { TrieNode() }
            }
            if (node.token == null) {
                sizeTracker.incrementAndGet()
            }
            node.token = token
        } finally {
            lock.writeLock().unlock()
        }
    }

    override fun size(): Int = sizeTracker.get()

    override fun contains(text: String): Boolean {
        val normalized = normalize(text)
        lock.readLock().lock()
        return try {
            navigate(normalized)?.token != null
        } finally {
            lock.readLock().unlock()
        }
    }

    private fun navigate(prefix: String): TrieNode? {
        var node = root
        for (ch in prefix) {
            node = node.children[ch] ?: return null
        }
        return node
    }

    private fun collect(node: TrieNode, results: MutableList<Token>, limit: Int) {
        if (results.size >= limit) return
        node.token?.let { results.add(it) }
        if (results.size >= limit) return
        // Iterate children in sorted character order for deterministic output.
        node.children.keys.toList().sorted().forEach { ch ->
            node.children[ch]?.let { collect(it, results, limit) }
        }
    }

    private fun normalize(text: String): String {
        return text.lowercase().trim()
    }
}
