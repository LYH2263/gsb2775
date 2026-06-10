<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import axios from 'axios'

interface Profile {
  name: string
  title: string
  location: string
  email: string
  phone: string
  githubUrl: string
  websiteUrl: string
  summary: string
}

interface Education {
  school: string
  degree: string
  major: string
  startDate: string
  endDate: string
  description: string
}

interface Experience {
  company: string
  position: string
  location: string
  startDate: string
  endDate: string
  description: string
}

interface Project {
  name: string
  role: string
  startDate: string
  endDate: string
  description: string
  githubUrl: string
  demoUrl: string
  techStack: string
}

interface Skill {
  name: string
  category: string
  level: number
  description: string
}

interface ResumeResponse {
  profile?: Profile
  educations: Education[]
  experiences: Experience[]
  projects: Project[]
  skills: Skill[]
}

const loading = ref(true)
const error = ref<string | null>(null)
const resume = ref<ResumeResponse | null>(null)
const selectedCategory = ref<string>('all')

const MASTERY_THRESHOLD = 90

const skillCategories = computed(() => {
  if (!resume.value?.skills) return []
  const categories = [...new Set(resume.value.skills.map(s => s.category))]
  return categories
})

const categoryCountMap = computed(() => {
  const map: Record<string, number> = { all: 0 }
  if (!resume.value?.skills) return map
  map.all = resume.value.skills.length
  for (const skill of resume.value.skills) {
    map[skill.category] = (map[skill.category] || 0) + 1
  }
  return map
})

const filteredSkills = computed(() => {
  if (!resume.value?.skills) return []
  if (selectedCategory.value === 'all') return resume.value.skills
  return resume.value.skills.filter(s => s.category === selectedCategory.value)
})

const apiBaseUrl = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8207/api/v1'

async function fetchResume() {
  loading.value = true
  error.value = null
  try {
    const res = await axios.get<ResumeResponse>(`${apiBaseUrl}/resume`)
    resume.value = res.data
  } catch (e) {
    error.value = '加载简历数据失败，请稍后重试。'
    console.error(e)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchResume()
})
</script>

<template>
  <div class="page">
    <div class="background-gradient" />
    <main class="content">
      <section class="profile-section" v-if="resume?.profile">
        <el-card class="profile-card" shadow="hover">
          <div class="profile-header">
            <div class="avatar">{{ resume.profile?.name?.charAt(0) ?? '' }}</div>
            <div>
              <h1 class="name">{{ resume.profile?.name }}</h1>
              <p class="title">{{ resume.profile?.title }}</p>
            </div>
          </div>
          <p class="summary">
            {{ resume.profile?.summary }}
          </p>
          <div class="profile-meta">
            <el-tag type="info" round>{{ resume.profile?.location }}</el-tag>
            <el-link :href="`mailto:${resume.profile?.email}`" target="_blank" underline="hover">
              {{ resume.profile?.email }}
            </el-link>
            <span>{{ resume.profile?.phone }}</span>
            <el-link v-if="resume.profile?.githubUrl" :href="resume.profile.githubUrl" target="_blank" underline="hover">
              GitHub
            </el-link>
            <el-link v-if="resume.profile?.websiteUrl" :href="resume.profile.websiteUrl" target="_blank" underline="hover">
              Website
            </el-link>
          </div>
        </el-card>
      </section>

      <section v-if="loading" class="loading-section">
        <el-skeleton :rows="6" animated />
      </section>

      <section v-else-if="error" class="error-section">
        <el-result icon="warning" title="加载失败" :sub-title="error">
          <template #extra>
            <el-button type="primary" @click="fetchResume">重试</el-button>
          </template>
        </el-result>
      </section>

      <section v-else class="grid">
        <div class="left-column">
          <el-card v-if="resume?.skills?.length" class="card" shadow="hover">
            <h2 class="section-title">技能特长</h2>
            <div class="skill-filter">
              <button
                class="filter-btn"
                :class="{ active: selectedCategory === 'all' }"
                @click="selectedCategory = 'all'"
              >
                全部 ({{ categoryCountMap.all }})
              </button>
              <button
                v-for="cat in skillCategories"
                :key="cat"
                class="filter-btn"
                :class="{ active: selectedCategory === cat }"
                @click="selectedCategory = cat"
              >
                {{ cat }} ({{ categoryCountMap[cat] || 0 }})
              </button>
            </div>
            <div class="skills">
              <div v-for="skill in filteredSkills" :key="skill.name" class="skill-item">
                <div class="skill-header">
                  <div class="skill-name-wrap">
                    <span class="skill-name">{{ skill.name }}</span>
                    <el-tag
                      v-if="skill.level >= MASTERY_THRESHOLD"
                      size="small"
                      type="danger"
                      effect="dark"
                      round
                      class="mastery-badge"
                    >
                      精通
                    </el-tag>
                  </div>
                  <el-tag size="small" type="success" round>{{ skill.category }}</el-tag>
                </div>
                <el-progress :percentage="skill.level" :stroke-width="8" striped />
                <p v-if="skill.description" class="skill-desc">{{ skill.description }}</p>
              </div>
            </div>
          </el-card>

          <el-card v-if="resume?.educations?.length" class="card" shadow="hover">
            <h2 class="section-title">教育背景</h2>
            <el-timeline>
              <el-timeline-item
                v-for="edu in resume!.educations"
                :key="edu.school + edu.startDate"
                :timestamp="`${edu.startDate} - ${edu.endDate}`"
              >
                <h3>{{ edu.school }}</h3>
                <p>{{ edu.degree }} · {{ edu.major }}</p>
                <p class="muted">{{ edu.description }}</p>
              </el-timeline-item>
            </el-timeline>
          </el-card>
        </div>

        <div class="right-column">
          <el-card v-if="resume?.experiences?.length" class="card" shadow="hover">
            <h2 class="section-title">工作经历</h2>
            <div v-for="exp in resume!.experiences" :key="exp.company + exp.startDate" class="item-block">
              <div class="item-header">
                <div>
                  <h3>{{ exp.position }}</h3>
                  <p class="muted">{{ exp.company }} · {{ exp.location }}</p>
                </div>
                <span class="muted">{{ exp.startDate }} - {{ exp.endDate }}</span>
              </div>
              <p class="item-desc">{{ exp.description }}</p>
            </div>
          </el-card>

          <el-card v-if="resume?.projects?.length" class="card" shadow="hover">
            <h2 class="section-title">项目经验</h2>
            <div class="projects-grid">
              <el-card
                v-for="project in resume!.projects"
                :key="project.name + project.startDate"
                class="project-card"
                shadow="hover"
              >
                <div class="item-header">
                  <div>
                    <h3>{{ project.name }}</h3>
                    <p class="muted">{{ project.role }}</p>
                  </div>
                  <span class="muted">{{ project.startDate }} - {{ project.endDate }}</span>
                </div>
                <p class="item-desc">{{ project.description }}</p>
                <div class="tags" v-if="project.techStack">
                  <el-tag
                    v-for="tag in project.techStack.split(',').map(t => t.trim()).filter(Boolean)"
                    :key="tag"
                    size="small"
                    effect="light"
                    round
                  >
                    {{ tag }}
                  </el-tag>
                </div>
                <div class="links">
                  <el-link v-if="project.githubUrl" :href="project.githubUrl" target="_blank" type="primary">
                    GitHub
                  </el-link>
                  <el-link v-if="project.demoUrl" :href="project.demoUrl" target="_blank">
                    Demo
                  </el-link>
                </div>
              </el-card>
            </div>
          </el-card>
        </div>
      </section>
    </main>
  </div>
</template>

<style scoped>
.page {
  position: relative;
  min-height: 100vh;
  background: radial-gradient(circle at top left, #e0f2fe, transparent 55%),
    radial-gradient(circle at bottom right, #e5e7eb, transparent 55%);
  color: #111827;
  padding: 24px 12px;
}

.background-gradient {
  display: none;
}

.content {
  position: relative;
  z-index: 1;
  max-width: 1200px;
  margin: 0 auto;
}

.profile-section {
  margin-bottom: 16px;
}

.profile-card {
  background: linear-gradient(120deg, #f9fafb, #eff6ff);
  border-radius: 16px;
  border: 1px solid #e5e7eb;
  box-shadow: 0 8px 24px rgba(15, 23, 42, 0.06);
}

.profile-header {
  display: flex;
  align-items: center;
  gap: 16px;
  margin-bottom: 8px;
}

.avatar {
  width: 56px;
  height: 56px;
  border-radius: 999px;
  background: linear-gradient(145deg, #38bdf8, #6366f1);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 24px;
  font-weight: 700;
  color: #ffffff;
}

.name {
  margin: 0;
  font-size: 24px;
  font-weight: 700;
}

.title {
  margin: 4px 0 0;
  font-size: 14px;
  color: #4b5563;
}

.summary {
  margin-top: 8px;
  margin-bottom: 10px;
  font-size: 13px;
  color: #4b5563;
}

.profile-meta {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  align-items: center;
  font-size: 12px;
  color: #374151;
}

.grid {
  display: grid;
  grid-template-columns: minmax(0, 0.95fr) minmax(0, 1.25fr);
  gap: 14px;
}

.left-column,
.right-column {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.card {
  background: #ffffff;
  border-radius: 14px;
  border: 1px solid #e5e7eb;
  box-shadow: 0 6px 18px rgba(15, 23, 42, 0.04);
}

.section-title {
  margin: 0 0 10px;
  font-size: 16px;
  font-weight: 600;
  color: #111827;
}

.skill-filter {
  display: flex;
  flex-wrap: wrap;
  gap: 6px;
  margin-bottom: 12px;
  padding-bottom: 10px;
  border-bottom: 1px solid #f3f4f6;
}

.filter-btn {
  padding: 4px 12px;
  font-size: 12px;
  font-weight: 500;
  color: #4b5563;
  background: #f9fafb;
  border: 1px solid #e5e7eb;
  border-radius: 999px;
  cursor: pointer;
  transition: all 0.2s ease;
}

.filter-btn:hover {
  color: #2563eb;
  background: #eff6ff;
  border-color: #bfdbfe;
}

.filter-btn.active {
  color: #ffffff;
  background: linear-gradient(135deg, #38bdf8, #6366f1);
  border-color: transparent;
}

.skills {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.skill-item {
  padding: 6px 0;
  border-bottom: 1px dashed #e5e7eb;
}

.skill-item:last-child {
  border-bottom: none;
}

.skill-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2px;
}

.skill-name-wrap {
  display: flex;
  align-items: center;
  gap: 6px;
}

.skill-name {
  font-weight: 500;
}

.mastery-badge {
  font-weight: 500;
}

.skill-desc {
  margin-top: 2px;
  font-size: 11px;
  color: #6b7280;
}

.item-block {
  padding: 6px 0;
  border-bottom: 1px dashed #e5e7eb;
}

.item-block:last-child {
  border-bottom: none;
}

.item-header {
  display: flex;
  justify-content: space-between;
  gap: 12px;
  align-items: flex-start;
}

.item-desc {
  margin-top: 4px;
  font-size: 13px;
  color: #4b5563;
}

.muted {
  font-size: 11px;
  color: #6b7280;
}

.projects-grid {
  display: grid;
  grid-template-columns: minmax(0, 1fr);
  gap: 10px;
}

.project-card {
  background: #f9fafb;
  border-radius: 12px;
  border: 1px solid #e5e7eb;
}

.tags {
  margin-top: 6px;
  display: flex;
  flex-wrap: wrap;
  gap: 4px;
}

.links {
  margin-top: 6px;
  display: flex;
  gap: 8px;
}

.loading-section,
.error-section {
  margin-top: 16px;
}

@media (max-width: 900px) {
  .grid {
    grid-template-columns: minmax(0, 1fr);
  }

  .page {
    padding: 16px 10px 24px;
  }
}
</style>

