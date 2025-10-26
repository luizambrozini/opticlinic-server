function LoginPagePartOne() {
  return (
    <div className="bg-linear-to-br from-blue-900 via-blue-950 to-indigo-950 w-5xl h-screen relative overflow-hidden">
      {/* Efeito de círculos decorativos */}
      <div className="absolute top-20 -left-20 w-40 h-40 bg-blue-400 rounded-full opacity-10 blur-3xl"></div>
      <div className="absolute bottom-32 -right-16 w-32 h-32 bg-cyan-300 rounded-full opacity-15 blur-2xl"></div>
      <div className="absolute top-1/2 left-1/4 w-24 h-24 bg-indigo-300 rounded-full opacity-10 blur-xl"></div>

      <div className="flex flex-col items-center justify-center h-full text-white relative z-10">
        <div className="flex items-center space-x-1 mb-2">
          <div className="w-12 h-12 bg-white text-blue-950  flex items-center justify-center shadow-lg font-bold text-4xl">
            O
          </div>
          <h1 className="text-3xl font-semibold tracking-widest">pticlinic</h1>
        </div>
        <p className="text-blue-200 text-sm mt-2 opacity-80">
          Sistema de Gestão de Clínicas
        </p>
      </div>

      <div className="absolute bottom-6 left-6 text-blue-200 text-xs opacity-70">
        © {new Date().getFullYear()} Opticlinic. Todos os direitos reservados.
      </div>
    </div>
  );
}

function LoginPagePartTwo() {
  return (
    <div className="bg-linear-to-l from-blue-50 to-white flex-1 flex items-center justify-center">
      <div className="w-full max-w-md px-8">
        <div className="bg-white rounded-2xl shadow-2xl p-8 border border-blue-100">
          <div className="text-center mb-8">
            <h2 className="text-2xl font-bold text-gray-800 mb-2">Bem-vindo</h2>
            <p className="text-gray-600 text-sm">
              Entre com suas credenciais para acessar o sistema
            </p>
          </div>

          <form className="space-y-6">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Email ou Usuário
              </label>
              <input
                type="text"
                className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all duration-200 outline-none"
                placeholder="Digite seu email ou usuário"
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Senha
              </label>
              <input
                type="password"
                className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all duration-200 outline-none"
                placeholder="Digite sua senha"
              />
            </div>

            <div className="flex items-center justify-between">
              <label className="flex items-center">
                <input
                  type="checkbox"
                  className="rounded border-gray-300 text-blue-600 focus:ring-blue-500"
                />
                <span className="ml-2 text-sm text-gray-600">Lembrar-me</span>
              </label>
              <a
                href="#"
                className="text-sm text-blue-600 hover:text-blue-800 transition-colors"
              >
                Esqueceu a senha?
              </a>
            </div>

            <button
              type="submit"
              className="w-full bg-blue-600 text-white py-3 px-4 rounded-lg hover:bg-blue-700 focus:ring-4 focus:ring-blue-200 transition-all duration-200 font-medium"
            >
              Entrar
            </button>
          </form>
        </div>
      </div>
    </div>
  );
}

function LoginPage() {
  return (
    <div className="flex">
      <LoginPagePartOne />
      <LoginPagePartTwo />
    </div>
  );
}

export default LoginPage;
