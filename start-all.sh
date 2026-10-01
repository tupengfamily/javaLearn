#!/bin/bash
# ============================================
# 一键启动前后端项目
# ============================================
# 后端: Spring Boot (http://localhost:8080)
# 前端: Vue 3 + Vite (http://localhost:5173)

set -e

# 颜色输出
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
CYAN='\033[0;36m'
NC='\033[0m' # No Color

# 工作目录(脚本所在目录)
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

# 日志与 PID 文件
mkdir -p "$SCRIPT_DIR/.run"
BACKEND_LOG="/tmp/springboot.log"
FRONTEND_LOG="/tmp/frontend.log"
BACKEND_PID_FILE="$SCRIPT_DIR/.run/backend.pid"
FRONTEND_PID_FILE="$SCRIPT_DIR/.run/frontend.pid"

# 打印彩色输出
info()    { echo -e "${BLUE}[INFO]${NC} $1"; }
success() { echo -e "${GREEN}[✓]${NC} $1"; }
warn()    { echo -e "${YELLOW}[!]${NC} $1"; }
error()   { echo -e "${RED}[✗]${NC} $1"; }

# ===========================================
# 检查环境依赖
# ===========================================
echo ""
echo -e "${CYAN}============================================${NC}"
echo -e "${CYAN}  Java 全栈项目 - 一键启动${NC}"
echo -e "${CYAN}============================================${NC}"
echo ""

info "检查环境..."

command -v java >/dev/null 2>&1 || { error "未找到 java,请先安装 JDK 17+"; exit 1; }
command -v mvn >/dev/null 2>&1 || { error "未找到 mvn,请先安装 Maven"; exit 1; }
command -v node >/dev/null 2>&1 || { error "未找到 node,请先安装 Node.js 18+"; exit 1; }
command -v npm >/dev/null 2>&1 || { error "未找到 npm,请先安装 npm"; exit 1; }

success "环境检查通过"
echo "  Java:  $(java -version 2>&1 | head -1)"
echo "  Maven: $(mvn -version 2>&1 | head -1)"
echo "  Node:  $(node -v)"
echo "  npm:   $(npm -v)"

# ===========================================
# 检查端口是否已被占用
# ===========================================
check_port() {
    local port=$1
    if lsof -ti :$port >/dev/null 2>&1; then
        echo "$port"
    fi
}

occupied=$(check_port 8080)
if [ -n "$occupied" ]; then
    warn "端口 8080 已被占用(可能已有 Spring Boot 运行)"
    warn "如需重启,请先执行: ./stop-all.sh"
fi

occupied=$(check_port 5173)
if [ -n "$occupied" ]; then
    warn "端口 5173 已被占用(可能已有 Vite 运行)"
    warn "如需重启,请先执行: ./stop-all.sh"
fi

# ===========================================
# 启动后端
# ===========================================
echo ""
info "启动后端 Spring Boot ..."
cd "$SCRIPT_DIR/spring-boot-learning"

# 后台启动 mvn
nohup mvn spring-boot:run > "$BACKEND_LOG" 2>&1 &
BACKEND_PID=$!
echo $BACKEND_PID > "$BACKEND_PID_FILE"

info "后端进程 PID: $BACKEND_PID,日志: $BACKEND_LOG"

# 等待后端就绪(最多 60 秒)
info "等待后端启动(端口 8080)..."
for i in {1..30}; do
    sleep 2
    if curl -sf http://localhost:8080/api/hello >/dev/null 2>&1; then
        success "后端启动成功"
        break
    fi
    if [ $i -eq 30 ]; then
        error "后端启动超时,请查看日志: tail -f $BACKEND_LOG"
        exit 1
    fi
done

# ===========================================
# 启动前端
# ===========================================
echo ""
info "启动前端 Vue + Vite ..."
cd "$SCRIPT_DIR/frontend-learning"

# 检查 node_modules 是否存在
if [ ! -d "node_modules" ]; then
    warn "未找到 node_modules,正在安装依赖..."
    npm install --no-audit --no-fund
fi

# 后台启动 Vite
nohup npm run dev > "$FRONTEND_LOG" 2>&1 &
FRONTEND_PID=$!
echo $FRONTEND_PID > "$FRONTEND_PID_FILE"

info "前端进程 PID: $FRONTEND_PID,日志: $FRONTEND_LOG"

# 等待前端就绪(最多 30 秒)
info "等待前端启动(端口 5173)..."
for i in {1..15}; do
    sleep 2
    if curl -sf http://localhost:5173 >/dev/null 2>&1; then
        success "前端启动成功"
        break
    fi
    if [ $i -eq 15 ]; then
        error "前端启动超时,请查看日志: tail -f $FRONTEND_LOG"
        exit 1
    fi
done

# ===========================================
# 总结
# ===========================================
echo ""
echo -e "${CYAN}============================================${NC}"
echo -e "${GREEN}  ✓ 前后端项目全部启动成功!${NC}"
echo -e "${CYAN}============================================${NC}"
echo ""
echo -e "  ${YELLOW}🎨 前端(Vue 3 + Element Plus)${NC}"
echo -e "     http://localhost:5173"
echo ""
echo -e "  ${YELLOW}☕ 后端(Spring Boot 3)${NC}"
echo -e "     http://localhost:8080"
echo -e "     H2 控制台: http://localhost:8080/h2-console"
echo ""
echo -e "  ${BLUE}📋 查看日志${NC}"
echo -e "     tail -f $BACKEND_LOG"
echo -e "     tail -f $FRONTEND_LOG"
echo ""
echo -e "  ${RED}🛑 停止服务${NC}"
echo -e "     ./stop-all.sh"
echo ""

# 询问是否打开浏览器
if command -v open >/dev/null 2>&1; then
    read -p "$(echo -e ${CYAN}是否打开浏览器? [y/N]: ${NC})" -n 1 -r
    echo ""
    if [[ $REPLY =~ ^[Yy]$ ]]; then
        open http://localhost:5173
    fi
fi