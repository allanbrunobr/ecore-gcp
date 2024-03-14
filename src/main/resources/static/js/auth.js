const firebaseConfig = {
    apiKey: "AIzaSyCbOdCDfzeM8BoFsnSOM_Kj8EQChR_bIhQ",
    authDomain: "login-69a6a.firebaseapp.com",
    projectId: "login-69a6a",
    storageBucket: "login-69a6a.appspot.com",
    messagingSenderId: "224357539251",
    appId: "1:224357539251:web:20981ebf0908365de0ffc6"
};

firebase.initializeApp(firebaseConfig);

const auth = firebase.auth();
const googleProvider = new firebase.auth.GoogleAuthProvider();
const googleLoginBtn = document.getElementById('google-login-btn');
if (googleLoginBtn) {
    const googleLogin = googleLoginBtn.addEventListener('click', async function () {
            await auth.signInWithPopup(googleProvider)
                .then(function (result) {
                    const credential = firebase.auth.GoogleAuthProvider.credentialFromResult(result);
                    const user = result.user;
                    console.log(user);
                    window.location.href = "/main";
                }).catch((error) => {
                    const errorCode = error.code;
                    const errorMessage = error.message;
                });
        });
}


const logoutLink = document.getElementById('logout-link');
if (logoutLink) {
    logoutLink.addEventListener('click', function () {
        auth.signOut()
            .then(() => {
                console.log('Usuário deslogado com sucesso');
                window.location.href = "/logout"; // redireciona para a página de login
            })
            .catch((error) => {
                console.log('Erro ao deslogar usuário: ', error);
            });
    });

}
// onAuthStateChanged(auth, (user) => {
//     if (user) {
//         window.location.href = "../../templates/main.html"; // Redirect to main page
//     } else {
//         window.location.href = "../index.html"; // Redirect to main page
//     }
// });

// Github
//
// const githubProvider = new firebase.auth.GithubAuthProvider();
// const githubLogin = document.getElementById('github-login-btn')
//     .addEventListener('click', async function () {
//         await auth.signInWithPopup(githubProvider)
//             .then(function(result){
//                 const credential = firebase.auth.GithubAuthProvider.credentialFromResult(result);
//                 const user = result.user;
//                 console.log(user);
//                 window.location.href = "../pages/main.html";
//             }).catch((error) => {
//             const errorCode = error.code;
//             const errorMessage = error.message;
//         });
//     });
//

