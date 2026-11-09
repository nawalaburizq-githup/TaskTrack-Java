# Technical Notes | ملاحظات تقنية

## 1. Docker Problem | المشكلة اللي واجهتني مع Docker

**Problem:**

When I tried to build my Docker Image for the first time, I got this error:

`The Dockerfile cannot be empty`

أول مشكلة واجهتني كانت لما حاولت أعمل Docker Build، وطلع عندي خطأ إنه ملف Dockerfile فاضي، وعشان هيك ما اكتمل البناء.

**Solution:**

I checked the Dockerfile and found that it was empty. I added the required instructions, saved the file, and tried again.

`docker build -t tasktrack-java .`

After that, the image was built successfully.

رجعت فتحت ملف Dockerfile، وضفت الأوامر المطلوبة وحفظته، وبعدها جربت أعمل Build مرة ثانية ونجح معي.

**What I Learned:**

I learned that I should check and save the Dockerfile before building the image.

تعلمت إنه لازم أتأكد من محتوى Dockerfile وأحفظ أي تعديل بعمله قبل ما أبدأ الـBuild.

## 2. Git & GitHub | تجربتي مع Git وGitHub

**Problem:**

When I tried to push the project to GitHub, I got this error:

`src refspec main does not match any`

واجهتني كمان مشكلة لما حاولت أرفع المشروع على GitHub، لأنه ظهر خطأ وما قدرت أعمل Push.

**Solution:**

I found that I was using Git commands in the wrong folder. I moved to the correct project folder, created my first commit, and pushed the project to GitHub.

اكتشفت إني كنت بكتب أوامر Git من مجلد غير مجلد المشروع الصحيح. بعد ما دخلت على المجلد الصحيح وعملت أول Commit، قدرت أرفع المشروع على GitHub.

**What I Learned:**

I learned that it is important to work in the correct Git repository and use clear commit messages.

تعلمت إنه لازم أنتبه للمجلد اللي بشتغل فيه، وكمان أكتب رسالة واضحة لكل Commit عشان أعرف شو التعديلات اللي عملتها.

## 3. Conclusion | الخلاصة

This assignment helped me understand how to run a Java application using Docker and how to manage my project using Git and GitHub.

من خلال هاد الواجب، فهمت بشكل عملي كيف أحوّل مشروع Java إلى Docker Image وأشغّله داخل Container، وكيف أستخدم Git وGitHub لحفظ المشروع وتنظيم التعديلات عليه.