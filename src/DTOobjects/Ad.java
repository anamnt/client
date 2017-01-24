package DTOobjects;


public class Ad {

    // Attributer for Ad oprettes
    private int adId;
    private int userId;
    private Long isbn;
    private int price;
    private int rating;
    private int deleted;
    private String comment;
    private int locked;
    private String userUsername;
    private int userPhonenumber;
    private String userAddress;
    private int userMobilepay;
    private int userCash;
    private int userTransfer;
    private String bookTitle;
    private String bookEdition;
    private String bookAuthor;

    public Ad() {

    }

    // Getters og setters oprettes

    public int getId() {
        return adId;
    }

    public void setId(int adId) {
        this.adId = adId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getLocked() {
        return locked;
    }

    public void setLocked(int locked) {
        this.locked = locked;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserPhonenumber(int userPhonenumber) {
        this.userPhonenumber = userPhonenumber;
    }

    public int getUserPhonenumber() {
        return userPhonenumber;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserMobilepay(int userMobilepay) {
        this.userMobilepay = userMobilepay;
    }

    public int getUserMobilepay() {
        return userMobilepay;
    }

    public void setUserCash(int userCash) {
        this.userCash = userCash;
    }

    public int getUserCash() {
        return userCash;
    }

    public void setUserTransfer(int userTransfer) {
        this.userTransfer = userTransfer;
    }

    public int getUserTransfer() {
        return userTransfer;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public Long getIsbn() {
        return isbn;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookEdition(String bookEdition) {
        this.bookEdition = bookEdition;
    }

    public String getBookEdition() {
        return bookEdition;
    }
    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }
}

