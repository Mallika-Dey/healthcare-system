export class ReviewModel {
  constructor({ doctorId = "", reviewText = "" }) {
    this.doctorId = doctorId;
    this.reviewText = reviewText;
  }
}
