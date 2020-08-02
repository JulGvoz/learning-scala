package futures

object SyncExample {
  var counter = 0
  synchronized {
    counter = counter + 1
  }
}