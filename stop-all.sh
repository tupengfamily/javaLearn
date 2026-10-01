#!/bin/bash
# ============================================
# 一键停止前后端项目
# ============================================

# 颜色
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m'

info()    { echo -e "${BLUE}[INFO]${NC} $1"; }
success() { echo -e "${GREEN}[✓]${NC} $1"; }
warn()    { echo -e "${YELLOW}[!]${NC} $1"; }
error()   { echo -e "${RED}[✗]${NC} $1"; }

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

mkdir -p "$SCRIPT_DIR/.run"
BACKEND_PID_FILE="$SCRIPT_DIR/.run/backend.pid"
FRONTEND_PID_FILE="$SCRIPT_DIR/.run/frontend.pid"

echo ""
echo -e "${BLUE}============================================${NC}"
echo -e "${BLUE}  停止前后端项目${NC}"
echo -e "${BLUE}============================================${NC}"
echo ""

# 通过端口找 PID(最准确)
kill_by_port() {
    local port=$1
    local name=$2
    local pids=$(lsof -ti :$port 2>/dev/null)
    if [ -n "$pids" ]; then
        warn "端口 $port 上的进程: $pids"
        echo "$pids" | xargs kill -TERM 2>/dev/null
        sleep 1
        # 强制 kill
        local remaining=$(lsof -ti :$port 2>/dev/null)
        if [ -n "$remaining" ]; then
            echo "$remaining" | xargs kill -9 2>/dev/null
        fi
        success "$name 已停止"
    else
        info "端口 $port 未被占用,$name 未运行"
    fi
}

# 停止后端(端口 8080)
info "停止后端 Spring Boot (端口 8080)..."
kill_by_port 8080 "后端服务"

# 停止前端(端口 5173)
info "停止前端 Vite (端口 5173)..."
kill_by_port 5173 "前端服务"

# 清理 PID 文件
[ -f "$BACKEND_PID_FILE" ] && rm -f "$BACKEND_PID_FILE"
[ -f "$FRONTEND_PID_FILE" ] && rm -f "$FRONTEND_PID_FILE"

# 最后兜底:用 PID 文件清理
if [ -f "$BACKEND_PID_FILE" ] || [ -f "$FRONTEND_PID_FILE" ]; then
    info "清理 PID 文件..."
fi

# 兜底:杀掉残留的 mvn 和 vite 进程(可选)
# 因为 mvn spring-boot:run 派生 java 进程,可能 lsof 找不到
info "清理残留进程..."

# 杀掉所有 mvn spring-boot 进程(包括派生的 java)
extra_pids=$(ps aux | grep -E "(spring-boot:run|SpringBootLearningApplication)" | grep -v grep | awk '{print $2}')
if [ -n "$extra_pids" ]; then
    echo "$extra_pids" | xargs kill -9 2>/dev/null
    success "清理了 Spring Boot 相关进程"
fi

# 杀掉所有 vite 进程
extra_pids=$(ps aux | grep -E "vite" | grep -v grep | awk '{print $2}')
if [ -n "$extra_pids" ]; then
    echo "$extra_pids" | xargs kill -9 2>/dev/null
    success "清理了 Vite 相关进程"
fi

echo ""
echo -e "${GREEN}============================================${NC}"
echo -e "${GREEN}  ✓ 所有服务已停止${NC}"
echo -e "${GREEN}============================================${NC}"
echo ""

# --clean 选项:同时清理 SQLite WAL/SHM 日志
if [ "$1" = "--clean" ]; then
    warn "清理 SQLite 临时文件..."
    rm -f "$SCRIPT_DIR/spring-boot-learning/data/"*-wal "$SCRIPT_DIR/spring-boot-learning/data/"*-shm "$SCRIPT_DIR/spring-boot-learning/data/"*-journal 2>/dev/null
    success "SQLite 临时文件已清理"
fi