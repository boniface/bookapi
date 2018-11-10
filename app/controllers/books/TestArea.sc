import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

val chapterTitle = "Helping Hands On Deck"
val ar = chapterTitle.split(" ").map(_.toLowerCase).mkString("-")

case class Chapter(
                    bookId: String,
                    chapterId: String,
                    chapterTitle: String,
                    chapterLink: String,
                  )

case class Book(
                 bookId: String,
                 bookTitle: String
               )

val c1 = Chapter("1", "1", "Helping Hands On Deck", "")
val c2 = Chapter("1", "2", "Enemy Is Powerless!", "")
val b1 = Book("1", "Little Black Book")

def getBooks: Future[Seq[Book]] = Future {
  Seq(b1)
}

def getBookById(id: String): Future[Option[Book]] = {
  val s = for {
    books <- getBooks
  } yield books.find(b => b.bookId == id)
  s
}

def populate(maybeBook: Option[Book], chapter: Chapter) = {
  val b = maybeBook.getOrElse(null)
  val bookTitle = b.bookTitle
  val chapterTitle = chapter.chapterTitle
  val sublink = chapterTitle.split(" ").map(_.toLowerCase).mkString("-")
  val chapterLink = "/" + bookTitle + "/" + sublink
  chapter.copy(chapterLink = chapterLink)
}

def create(c: Chapter) = {
  val re = for {
    book <- getBookById(c.bookId)
//    cd <- populate(book, c)
  } yield book
  re.map(books => books.map(book => book))
  val s = re.flatMap {
    case Some(b) => b
    case None => None
  }
}
