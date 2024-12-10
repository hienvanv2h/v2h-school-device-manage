@import com.vanhuuhien99.school_device_management.projection.SchoolClassIdAndNameProjection
@import com.vanhuuhien99.school_device_management.projection.SubjectIdAndNameProjection
@import com.vanhuuhien99.school_device_management.projection.TeacherIdAndFullNameProjection
@import java.util.List

@param java.util.List<String> errors
@param String type
@param Long id
@param com.vanhuuhien99.school_device_management.formmodel.TeacherAssignmentForm teacherAssignmentForm

@param List<TeacherIdAndFullNameProjection> teacherList
@param List<SchoolClassIdAndNameProjection> schoolClassList
@param List<SubjectIdAndNameProjection> subjectList

@template.layout.dashboard(content = @`
    <div class="max-w-2xl mx-auto mt-8">
        <h2 class="text-2xl font-semibold text-gray-700 mb-4">Phân công chuyên môn</h2>
        <form name="teacherAssignmentForm"
              action="/dashboard/teacher-assignments/save${type == null || type.equals("create") ? "" : "/" + id}"
              method="POST"
              class="bg-white p-6 rounded-lg shadow-md">
            @if(errors != null)
                <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
                    <ul>
                        @for(var error: errors)
                            <li>${error}</li>
                        @endfor
                    </ul>
                </div>
            @endif
            <div class="flex flex-col gap-4">
                <%--    Hidden input for PUT method    --%>
                @if(type != null && type.equals("update"))
                    <input type="hidden" name="_method" value="PUT">
                @endif

                <!-- Teacher Dropdown -->
                <div class="dropdown relative">
                    <label class="block text-gray-700 font-medium mb-2">
                        Giáo viên <span class="text-red-500">*</span>
                    </label>
                    <input type="hidden" name="teacherId"
                           value="${type != null && type.equals("update") && teacherAssignmentForm != null && teacherAssignmentForm.getTeacherId() != null
                                    ? teacherAssignmentForm.getTeacherId().toString() : ""}"
                           class="dropdown-input" />
                    <button type="button" class="dropdown-button py-2 px-4 border border-gray-300 rounded-md w-full text-left flex justify-between items-center">
                        <span class="dropdown-selected">
                            @if(type != null && type.equals("update") && teacherList != null && !teacherList.isEmpty() && teacherAssignmentForm != null)
                                ${teacherList.stream()
                                        .filter(t -> t.getTeacherId().equals(teacherAssignmentForm.getTeacherId()))
                                        .map(TeacherIdAndFullNameProjection::getFullName)
                                        .findFirst()
                                        .orElse("")}
                            @else
                                ${"Chọn giáo viên"}
                            @endif
                        </span>
                        <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 transform transition-transform dropdown-icon" viewBox="0 0 20 20" fill="currentColor">
                            <path fill-rule="evenodd" d="M5.293 9.707a1 1 0 011.414 0L10 13.414l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z" clip-rule="evenodd" />
                        </svg>
                    </button>
                    <ul class="dropdown-menu absolute z-10 bg-white border border-gray-300 rounded-md shadow-lg mt-2 max-h-48 overflow-y-auto w-full hidden">
                        @if(teacherList != null && !teacherList.isEmpty())
                            @for(var teacher: teacherList)
                                <li data-id="${teacher.getTeacherId()}" class="dropdown-item px-4 py-2 hover:bg-gray-100 cursor-pointer">
                                    ${teacher.getFullName()}
                                </li>
                            @endfor
                        @else
                            <li class="dropdown-item px-4 py-2 text-gray-500">Không có giáo viên</li>
                        @endif
                    </ul>
                </div>

                <!-- Class Dropdown -->
                <div class="dropdown relative">
                    <label class="block text-gray-700 font-medium mb-2">
                        Lớp <span class="text-red-500">*</span>
                    </label>
                    <input type="hidden" name="classId"
                           value="${type != null && type.equals("update") && teacherAssignmentForm != null && teacherAssignmentForm.getClassId() != null
                                    ? teacherAssignmentForm.getClassId().toString() : ""}"
                           class="dropdown-input" />
                    <button type="button" class="dropdown-button py-2 px-4 border border-gray-300 rounded-md w-full text-left flex justify-between items-center">
                        <span class="dropdown-selected">
                            @if(type != null && type.equals("update") && schoolClassList != null && !schoolClassList.isEmpty() && teacherAssignmentForm != null)
                                ${schoolClassList.stream()
                                        .filter(t -> t.getClassId().equals(teacherAssignmentForm.getClassId()))
                                        .map(SchoolClassIdAndNameProjection::getClassName)
                                        .findFirst()
                                        .orElse("")}
                            @else
                                ${"Chọn lớp"}
                            @endif
                        </span>
                        <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 transform transition-transform dropdown-icon" viewBox="0 0 20 20" fill="currentColor">
                            <path fill-rule="evenodd" d="M5.293 9.707a1 1 0 011.414 0L10 13.414l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z" clip-rule="evenodd" />
                        </svg>
                    </button>
                    <ul class="dropdown-menu absolute z-10 bg-white border border-gray-300 rounded-md shadow-lg mt-2 max-h-48 overflow-y-auto w-full hidden">
                        @if(schoolClassList != null && !schoolClassList.isEmpty())
                            @for(var sClass: schoolClassList)
                                <li data-id="${sClass.getClassId()}" class="dropdown-item px-4 py-2 hover:bg-gray-100 cursor-pointer">
                                    ${sClass.getClassName()}
                                </li>
                            @endfor
                        @else
                            <li class="dropdown-item px-4 py-2 text-gray-500">Không có lớp học</li>
                        @endif
                    </ul>
                </div>

                <!-- Subject Dropdown -->
                <div class="dropdown relative">
                    <label class="block text-gray-700 font-medium mb-2">
                        Môn học <span class="text-red-500">*</span>
                    </label>
                    <input type="hidden" name="subjectId"
                           value="${type != null && type.equals("update") && teacherAssignmentForm != null && teacherAssignmentForm.getSubjectId() != null
                                    ? teacherAssignmentForm.getSubjectId().toString() : ""}"
                           class="dropdown-input" />
                    <button type="button" class="dropdown-button py-2 px-4 border border-gray-300 rounded-md w-full text-left flex justify-between items-center">
                        <span class="dropdown-selected">
                            @if(type != null && type.equals("update") && subjectList != null && !subjectList.isEmpty() && teacherAssignmentForm != null)
                                ${subjectList.stream()
                                        .filter(t -> t.getSubjectId().equals(teacherAssignmentForm.getSubjectId()))
                                        .map(SubjectIdAndNameProjection::getSubjectName)
                                        .findFirst()
                                        .orElse("")}
                            @else
                                ${"Chọn môn học"}
                            @endif
                        </span>
                        <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 transform transition-transform dropdown-icon" viewBox="0 0 20 20" fill="currentColor">
                            <path fill-rule="evenodd" d="M5.293 9.707a1 1 0 011.414 0L10 13.414l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z" clip-rule="evenodd" />
                        </svg>
                    </button>
                    <ul class="dropdown-menu absolute z-10 bg-white border border-gray-300 rounded-md shadow-lg mt-2 max-h-48 overflow-y-auto w-full hidden">
                        @if(subjectList != null && !subjectList.isEmpty())
                            @for(var subject: subjectList)
                                <li data-id="${subject.getSubjectId()}" class="dropdown-item px-4 py-2 hover:bg-gray-100 cursor-pointer">
                                    ${subject.getSubjectName()}
                                </li>
                            @endfor
                        @else
                            <li class="dropdown-item px-4 py-2 text-gray-500">Không có môn học</li>
                        @endif
                    </ul>
                </div>

                <div>
                    <label for="semester" class="block text-gray-700 font-medium mb-2">
                        Học kỳ <span class="text-red-500">*</span>
                    </label>
                    <input type="text" id="semester" name="semester"
                           value="${teacherAssignmentForm != null && teacherAssignmentForm.getSemester() != null ? teacherAssignmentForm.getSemester() : ""}"
                           class="px-4 py-2 w-full border border-gray-300 rounded-md mt-2"
                           required>
                </div>

                <div>
                    <label for="description" class="block text-gray-700 font-medium mb-2">
                        Mô tả
                    </label>
                    <input type="text" id="description" name="description"
                           value="${teacherAssignmentForm != null && teacherAssignmentForm.getDescription() != null ? teacherAssignmentForm.getDescription() : ""}"
                           class="px-4 py-2 w-full border border-gray-300 rounded-md mt-2">
                </div>
            </div>
            <div class="flex justify-end mt-4">
                <button type="submit" class="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600">
                    ${type == null || type.equals("create") ? "Thêm" : "Cập nhật"}
                </button>
            </div>
        </form>
    </div>
`,
customScripts = @`
    <%--    Script xử lý dropdown    --%>
    <script src="/js/dropdown.js"></script>
`)
